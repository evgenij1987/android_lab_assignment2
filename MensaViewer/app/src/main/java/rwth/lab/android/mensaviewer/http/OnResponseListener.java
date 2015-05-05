package rwth.lab.android.mensaviewer.http;

import rwth.lab.android.mensaviewer.model.WeekPlan;

/**
 * Created by ekaterina on 30.04.2015.
 */
public interface OnResponseListener {

    /**
     * The method is being triggered on the listener
     * before execution of a task
     */
    void onPreExecute();

    /**
     * The method is being triggered on the listener
     * as a response of a task
     */
    void onResponse(WeekPlan weekPlan);

    /**
     * The method is being triggered on the listener
     * when the error occurs during the task execution
     */
    void onError(String errorMessage);
}