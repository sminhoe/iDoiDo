package com.example.user8.myapplication;

import java.util.HashMap;
import java.util.Map;

public class AddTask {

    public String taskname, taskdetails, taskdate, tasktime, taskkey, taskstatus, taskfinishdate;

    public AddTask() {
    }

    public AddTask(String taskname, String taskdetails, String taskdate, String tasktime, String taskkey, String taskstatus, String taskfinishdate) {
        this.taskname = taskname;
        this.taskdetails = taskdetails;
        this.taskdate = taskdate;
        this.tasktime = tasktime;
        this.taskkey = taskkey;
        this.taskstatus = taskstatus;
        this.taskfinishdate = taskfinishdate;
    }

    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("taskname", taskname);
        result.put("taskdetails", taskdetails);
        result.put("taskdate", taskdate);
        result.put("tasktime", tasktime);
        result.put("taskkey", taskkey);
        result.put("taskstatus", taskstatus);
        result.put("taskfinishdate", taskfinishdate);

        return result;
    }

    public String getTaskfinishdate() {
        return taskfinishdate;
    }

    public void setTaskfinishdate(String taskfinishdate) {
        this.taskfinishdate = taskfinishdate;
    }

    public String getTaskstatus() {
        return taskstatus;
    }

    public void setTaskstatus(String taskstatus) {
        this.taskstatus = taskstatus;
    }

    public String getTaskkey() {
        return taskkey;
    }

    public void setTaskkey(String taskkey) {
        this.taskkey = taskkey;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getTaskdetails() {
        return taskdetails;
    }

    public void setTaskdetails(String taskdetails) {
        this.taskdetails = taskdetails;
    }

    public String getTaskdate() {
        return taskdate;
    }

    public void setTaskdate(String taskdate) {
        this.taskdate = taskdate;
    }

    public String getTasktime() {
        return tasktime;
    }

    public void setTasktime(String tasktime) {
        this.tasktime = tasktime;
    }

}
