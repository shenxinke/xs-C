package iview;

/**
 * @author Created by kangshuaiqiang on 2019/3/5
 */

public interface PlayStatus {
    /**
     * 获取当前进度，进度条，总时间
     * @param progress
     * @param position
     * @param duration
     */
    void getProgressAndText(int progress, int position, int duration);

    /**
     * 自动播放结束状态
     */
    void onStateAutoComplete();
}
