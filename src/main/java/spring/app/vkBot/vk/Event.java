package spring.app.vkBot.vk;

import spring.app.vkBot.common.Date;
import spring.app.vkBot.core.commands.ServiceCommand;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;


public class Event {

    private int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    private static HashMap<String, HashSet<ServiceCommand>> timedCommands = new HashMap<>();
    private static HashMap<String, Integer> timeLockList = new HashMap<>();

    public void handlePerDay(){

        for(String time : timedCommands.keySet()) {
            if (!isTimeLocked(time)) {
                if (Date.getTimeNow().equals(time)) {
                    for (ServiceCommand cmd : timedCommands.get(time)) {
                        cmd.service();
                    }
                    lockTime(time);
                }
            }
        }
    }

    public void addCommand(String time, ServiceCommand cmd){
        HashSet<ServiceCommand> serviceCommands;
        if (timedCommands.containsKey(time)){
            serviceCommands = timedCommands.get(time);
        } else{
            serviceCommands = new HashSet<>();
        }
        serviceCommands.add(cmd);
        timedCommands.put(time, serviceCommands);
    }

    private boolean isTimeLocked(String time){
        if (timeLockList.containsKey(time)) {
            if (timeLockList.get(time) == dayOfWeek) {
                return true;
            } else {
                timeLockList.remove(time);
                return false;
            }
        } else {
            return false;
        }
    }

    private void lockTime(String time) {
        timeLockList.put(time, dayOfWeek);
    }

}
