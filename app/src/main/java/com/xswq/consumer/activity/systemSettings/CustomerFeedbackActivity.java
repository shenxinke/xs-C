package com.xswq.consumer.activity.systemSettings;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xswq.consumer.ConsumerApplication;
import com.xswq.consumer.R;
import com.xswq.consumer.activity.main.MainActivity;
import com.xswq.consumer.base.BaseListAdapter;
import com.xswq.consumer.base.BaseListViewHolder;
import com.xswq.consumer.bean.CustomerFeedbcakShowBean;
import com.xswq.consumer.bean.FeedBackInfoBean;
import com.xswq.consumer.bean.FeedBackInfoQuestionBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.LogUtil;
import com.xswq.consumer.utils.RxCode;
import com.xswq.consumer.utils.ToastUtils;
import com.xswq.consumer.view.MyPopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import gorden.rxbus2.RxBus;
import gorden.rxbus2.Subscribe;
import gorden.rxbus2.ThreadMode;
import okhttp3.Call;


public class CustomerFeedbackActivity extends Activity {
    private List<FeedBackInfoQuestionBean> questionList;
    private ListView listView;
    private Button saveQustionButton;
    private String feedbackId;
    private String answer;
    private BaseListAdapter baseListAdapter;
    private BaseListViewHolder holder;
    private FeedBackInfoQuestionBean item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_feedback_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ImageView imageView = findViewById(R.id.set_up_shut_down);
        listView = findViewById(R.id.list_view);
        saveQustionButton = findViewById(R.id.save_qustion_button);
        saveQustionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer = new String();
                feedbackId = new String();
                if (questionList == null) return;
                for (int i = 0; i < questionList.size(); i++) {
                    FeedBackInfoQuestionBean feedBackInfoQuestionBean = questionList.get(i);
                    String id = feedBackInfoQuestionBean.getId();
                    String swithcBck = feedBackInfoQuestionBean.getSwithcBck();
                    int star = feedBackInfoQuestionBean.getStar();
                    if (star == 1 && TextUtils.isEmpty(swithcBck)) {
                        showPopupWindow("请完成第" + id + "题!");
                        return;
                    }
                    if (!TextUtils.isEmpty(swithcBck)) {
                        answer += swithcBck + "%%%";
                        feedbackId += id + "%%%";
                    }
                }
                if (!TextUtils.isEmpty(answer) && !TextUtils.isEmpty(feedbackId)) {
                    answer = answer.substring(0, answer.length() - 3);
                    feedbackId = feedbackId.substring(0, feedbackId.length() - 3);
                }
                LogUtil.e("aaa", "answer :" + answer);
                LogUtil.e("aaa", "feedbackId :" + feedbackId);
                if (!TextUtils.isEmpty(answer) && !TextUtils.isEmpty(feedbackId)) {
                    saveQustion();
                }
            }
        });
        getData();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    //获取问题
    private void getData() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_FEED_BACK_INFO)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    FeedBackInfoBean feedBackInfoBean = GsonUtil.gsonToBean(response, FeedBackInfoBean.class, CustomerFeedbackActivity.this);
                    if (feedBackInfoBean != null) {
                        List<FeedBackInfoBean.DataBean> data = feedBackInfoBean.getData();
                        if (data.size() > 0) {
                            questionList = new ArrayList<>();
                            for (int i = 0; i < data.size(); i++) {
                                FeedBackInfoQuestionBean feedBackInfoQuestionBean = new FeedBackInfoQuestionBean();
                                String answerA = data.get(i).getAnswerA();
                                String answerB = data.get(i).getAnswerB();
                                String answerC = data.get(i).getAnswerC();
                                String answerD = data.get(i).getAnswerD();
                                String answerId = data.get(i).getId();
                                int type = data.get(i).getType();
                                int star = data.get(i).getMustStage();
                                feedBackInfoQuestionBean.setStar(star);
                                feedBackInfoQuestionBean.setType(type);
                                feedBackInfoQuestionBean.setId(answerId);
                                String desciption = data.get(i).getDesciption();
                                if (desciption != null) {
                                    feedBackInfoQuestionBean.setDesciption(desciption);
                                }
                                if (answerB != null) {
                                    feedBackInfoQuestionBean.setAnswerB(answerB);
                                }
                                if (answerC != null) {
                                    feedBackInfoQuestionBean.setAnswerC(answerC);
                                }
                                if (answerD != null) {
                                    feedBackInfoQuestionBean.setAnswerD(answerD);
                                }
                                if (answerA != null) {
                                    feedBackInfoQuestionBean.setAnswerA(answerA);
                                }
                                questionList.add(feedBackInfoQuestionBean);
                            }
                        }
                        if (questionList != null && questionList.size() > 0) {
                            initAdapter();
                        }
                    } else {
                        ToastUtils.show(Const.CONS_STR_ERROR);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAdapter() {
        final int[] answer = {1, 1, 1, 1};

        baseListAdapter = new BaseListAdapter<FeedBackInfoQuestionBean>(ConsumerApplication.getInstance(), questionList, R.layout.customer_feed_back_item_layout) {
            @Override
            public void convert(final BaseListViewHolder holder, int position, final FeedBackInfoQuestionBean item) {
                int indext = position + 1;
                int type = item.getType();
                holder.setEtextGone(R.id.question_etext, 1);
                holder.setImageGone(R.id.check_a, 2);
                holder.setTextGone(R.id.text_answer_a, 2);
                holder.setImageGone(R.id.check_b, 2);
                holder.setTextGone(R.id.text_answer_b, 2);
                holder.setImageGone(R.id.check_c, 2);
                holder.setTextGone(R.id.text_answer_c, 2);
                holder.setImageGone(R.id.check_d, 2);
                holder.setTextGone(R.id.text_answer_d, 2);

                if (TextUtils.isEmpty(item.getAnswerA())) {
                    holder.setImageGone(R.id.check_a, 1);
                }
                if (TextUtils.isEmpty(item.getAnswerB())) {
                    holder.setImageGone(R.id.check_b, 1);
                }
                if (TextUtils.isEmpty(item.getAnswerC())) {
                    holder.setImageGone(R.id.check_c, 1);
                }
                if (TextUtils.isEmpty(item.getAnswerD())) {
                    holder.setImageGone(R.id.check_d, 1);
                }
                int star = item.getStar();
                //是否必选 1 必选  2 非必选
                if (star == 1) {
                    holder.setTextGone(R.id.start_text, 2);
                } else {
                    holder.setTextGone(R.id.start_text, 1);
                }
                switch (type) {
                    case 1:
                        holder.setText(R.id.text_question, indext + "." + item.getDesciption() + " (单选)");
                        if (type == 1) {
                            holder.setTextListener(R.id.text_answer_a, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    holder.setImageById(R.id.check_a, R.mipmap.settings_item_check);
                                    holder.setImageById(R.id.check_b, R.mipmap.settings_item_uncheck);
                                    holder.setImageById(R.id.check_c, R.mipmap.settings_item_uncheck);
                                    holder.setImageById(R.id.check_d, R.mipmap.settings_item_uncheck);
                                    item.setSwithcBck("A");
                                }
                            });
                            holder.setImageListener(R.id.check_a, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    holder.setImageById(R.id.check_a, R.mipmap.settings_item_check);
                                    holder.setImageById(R.id.check_b, R.mipmap.settings_item_uncheck);
                                    holder.setImageById(R.id.check_c, R.mipmap.settings_item_uncheck);
                                    holder.setImageById(R.id.check_d, R.mipmap.settings_item_uncheck);
                                    item.setSwithcBck("A");
                                }
                            });
                            holder.setTextListener(R.id.text_answer_b, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    holder.setImageById(R.id.check_a, R.mipmap.settings_item_uncheck);
                                    holder.setImageById(R.id.check_b, R.mipmap.settings_item_check);
                                    holder.setImageById(R.id.check_c, R.mipmap.settings_item_uncheck);
                                    holder.setImageById(R.id.check_d, R.mipmap.settings_item_uncheck);
                                    item.setSwithcBck("B");
                                }
                            });
                            holder.setImageListener(R.id.check_b, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    holder.setImageById(R.id.check_a, R.mipmap.settings_item_uncheck);
                                    holder.setImageById(R.id.check_b, R.mipmap.settings_item_check);
                                    holder.setImageById(R.id.check_c, R.mipmap.settings_item_uncheck);
                                    holder.setImageById(R.id.check_d, R.mipmap.settings_item_uncheck);
                                    item.setSwithcBck("B");
                                }
                            });
                            holder.setTextListener(R.id.text_answer_c, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    holder.setImageById(R.id.check_a, R.mipmap.settings_item_uncheck);
                                    holder.setImageById(R.id.check_b, R.mipmap.settings_item_uncheck);
                                    holder.setImageById(R.id.check_c, R.mipmap.settings_item_check);
                                    holder.setImageById(R.id.check_d, R.mipmap.settings_item_uncheck);
                                    item.setSwithcBck("C");
                                }
                            });
                            holder.setImageListener(R.id.check_c, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    holder.setImageById(R.id.check_a, R.mipmap.settings_item_uncheck);
                                    holder.setImageById(R.id.check_b, R.mipmap.settings_item_uncheck);
                                    holder.setImageById(R.id.check_c, R.mipmap.settings_item_check);
                                    holder.setImageById(R.id.check_d, R.mipmap.settings_item_uncheck);
                                    item.setSwithcBck("C");
                                }
                            });
                            holder.setTextListener(R.id.text_answer_d, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    holder.setImageById(R.id.check_a, R.mipmap.settings_item_uncheck);
                                    holder.setImageById(R.id.check_b, R.mipmap.settings_item_uncheck);
                                    holder.setImageById(R.id.check_c, R.mipmap.settings_item_uncheck);
                                    holder.setImageById(R.id.check_d, R.mipmap.settings_item_check);
                                    item.setSwithcBck("D");
                                }
                            });
                            holder.setImageListener(R.id.check_d, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    holder.setImageById(R.id.check_a, R.mipmap.settings_item_uncheck);
                                    holder.setImageById(R.id.check_b, R.mipmap.settings_item_uncheck);
                                    holder.setImageById(R.id.check_c, R.mipmap.settings_item_uncheck);
                                    holder.setImageById(R.id.check_d, R.mipmap.settings_item_check);
                                    item.setSwithcBck("D");
                                }
                            });
                        }
                        break;
                    case 2:
                        holder.setText(R.id.text_question, indext + "." + item.getDesciption() + " (多选)");
                        holder.setTextListener(R.id.text_answer_a, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (answer[0] == 1) {
                                    holder.setImageById(R.id.check_a, R.mipmap.settings_item_check);
                                    answer[0] = 2;
                                } else {
                                    holder.setImageById(R.id.check_a, R.mipmap.settings_item_uncheck);
                                    answer[0] = 1;
                                }
                            }
                        });
                        holder.setImageListener(R.id.check_a, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (answer[0] == 1) {
                                    holder.setImageById(R.id.check_a, R.mipmap.settings_item_check);
                                    answer[0] = 2;
                                } else {
                                    holder.setImageById(R.id.check_a, R.mipmap.settings_item_uncheck);
                                    answer[0] = 1;
                                }
                            }
                        });
                        holder.setTextListener(R.id.text_answer_b, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (answer[1] == 1) {
                                    holder.setImageById(R.id.check_b, R.mipmap.settings_item_check);
                                    answer[1] = 2;
                                } else {
                                    holder.setImageById(R.id.check_b, R.mipmap.settings_item_uncheck);
                                    answer[1] = 1;
                                }
                            }
                        });
                        holder.setImageListener(R.id.check_b, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (answer[1] == 1) {
                                    holder.setImageById(R.id.check_b, R.mipmap.settings_item_check);
                                    answer[1] = 2;
                                } else {
                                    holder.setImageById(R.id.check_b, R.mipmap.settings_item_uncheck);
                                    answer[1] = 1;
                                }
                            }
                        });
                        holder.setTextListener(R.id.text_answer_c, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (answer[2] == 1) {
                                    holder.setImageById(R.id.check_c, R.mipmap.settings_item_check);
                                    answer[2] = 2;
                                } else {
                                    holder.setImageById(R.id.check_c, R.mipmap.settings_item_uncheck);
                                    answer[2] = 1;
                                }
                            }
                        });
                        holder.setImageListener(R.id.check_c, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (answer[2] == 1) {
                                    holder.setImageById(R.id.check_c, R.mipmap.settings_item_check);
                                    answer[2] = 2;
                                } else {
                                    holder.setImageById(R.id.check_c, R.mipmap.settings_item_uncheck);
                                    answer[2] = 1;
                                }
                            }
                        });
                        holder.setTextListener(R.id.text_answer_d, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (answer[3] == 1) {
                                    holder.setImageById(R.id.check_d, R.mipmap.settings_item_check);
                                    answer[3] = 2;
                                } else {
                                    holder.setImageById(R.id.check_d, R.mipmap.settings_item_uncheck);
                                    answer[3] = 1;
                                }
                            }
                        });
                        holder.setImageListener(R.id.check_d, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (answer[3] == 1) {
                                    holder.setImageById(R.id.check_d, R.mipmap.settings_item_check);
                                    answer[3] = 2;
                                } else {
                                    holder.setImageById(R.id.check_d, R.mipmap.settings_item_uncheck);
                                    answer[3] = 1;
                                }
                            }
                        });
                        String string = "";
                        for (int i = 0; i < answer.length; i++) {
                            String a = "";
                            int j = answer[i];
                            if (j != 1) {
                                if (i == 0) {
                                    a = "A";
                                } else if (i == 1) {
                                    a = "B";
                                } else if (i == 2) {
                                    a = "C";
                                } else if (i == 3) {
                                    a = "D";
                                }
                            }
                            if (!TextUtils.isEmpty(a)) {
                                string += a + ",";
                            }
                        }
                        if (!TextUtils.isEmpty(string) && string.length() > 0) {
                            string = string.substring(0, string.length() - 1);
                            item.setSwithcBck(string);
                        }
                        break;
                    case 3:
                        holder.setText(R.id.text_question, indext + "." + item.getDesciption());
                        holder.setEtextGone(R.id.question_etext, 2);
                        holder.setImageGone(R.id.check_a, 1);
                        holder.setTextGone(R.id.text_answer_a, 1);
                        holder.setImageGone(R.id.check_b, 1);
                        holder.setTextGone(R.id.text_answer_b, 1);
                        holder.setImageGone(R.id.check_c, 1);
                        holder.setTextGone(R.id.text_answer_c, 1);
                        holder.setImageGone(R.id.check_d, 1);
                        holder.setTextGone(R.id.text_answer_d, 1);
                        EditText editText = holder.getView(R.id.question_etext);
                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                String s1 = s.toString();
                                if (!TextUtils.isEmpty(s1)) {
                                    item.setSwithcBck(s1);
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                        break;
                    default:
                        break;
                }
                holder.setText(R.id.text_answer_a, ConstUtils.getStringNoEmpty(item.getAnswerA()));
                holder.setText(R.id.text_answer_b, ConstUtils.getStringNoEmpty(item.getAnswerB()));
                holder.setText(R.id.text_answer_c, ConstUtils.getStringNoEmpty(item.getAnswerC()));
                holder.setText(R.id.text_answer_d, ConstUtils.getStringNoEmpty(item.getAnswerD()));
            }
        };

        listView.setAdapter(baseListAdapter);
    }

    private void saveQustion() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_SAVE_FEED_BACK_RECORD)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("feedbackId", feedbackId)
                    .addParams("answer", answer)
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    CustomerFeedbcakShowBean customerFeedbcakShowBean = GsonUtil.gsonToBean(response, CustomerFeedbcakShowBean.class, CustomerFeedbackActivity.this);
                    if (customerFeedbcakShowBean != null) {
                        int data = customerFeedbcakShowBean.getData();
                        if (data > 0) {
                            JumpIntent.jump(CustomerFeedbackActivity.this, ShowCdKeyConversion.class);
                        } else {
                            showPopupWindow("提交成功");
                        }
                    } else {
                        ToastUtils.show(Const.CONS_STR_ERROR);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //显示提示框
    private void showPopupWindow(String str) {
        View layout = LayoutInflater.from(CustomerFeedbackActivity.this).inflate(R.layout.pop_window_layout, null, false);
        View view = getWindow().getDecorView();
        MyPopupWindow.myPopupWindow(view, layout);
        Button btnSava = layout.findViewById(R.id.button_save);
        Button btnCancel = layout.findViewById(R.id.button_cancel);
        TextView texContent = layout.findViewById(R.id.popup_window_content);
        texContent.setText(str);
        btnSava.setText("确认");
        btnCancel.setVisibility(View.GONE);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_save:
                        MyPopupWindow.dismissPopWindow();
                        break;
                    case R.id.button_cancel:
                        MyPopupWindow.dismissPopWindow();
                        break;
                    default:
                        break;
                }
            }
        };
        //设置popwindow布局控件的点击事件
        btnSava.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);
    }
}
