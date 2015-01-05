package factory.model;

import java.awt.Toolkit;
import java.util.Date;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import javax.ejb.*;
import javax.annotation.Resource;

import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;

public class StillTimerTask {
	Timer timer;
	private TimerTask timertask;
	Still still;
	DepartmentRepository departmentrepository;
	Production production;
	int tasks = 0;
	int distillation = 10;

	
	
	public StillTimerTask(Still selectedStill, Production production) {
		still = selectedStill;
		production = production;
//		timer = new Timer();
		startTimer();

//		toolkit = Toolkit.getDefaultToolkit();
//		timer.schedule(new Reminder(),0, 1 * 1000);
//		timer.schedule(new Reminder(),5 * 1000, 1 * 1000);
	}
// 
//	class Reminder extends TimerTask {
//		int numWarningBeeps = 5;
//
//		public void run() 
//		{
//            if(tasks != 2)
//            {
//				System.out.println("+ " + numWarningBeeps);
//				numWarningBeeps--;
//	            
//	            if (numWarningBeeps == 0) 
//	            {
//	                still.setStatus_two(false);
//	                tasks = 2;
//	    			timer.cancel(); // Terminate the timer thread
//	            }
//            }
//            
//            if(tasks == 2)
//            {
//                if (numWarningBeeps == 0) 
//                {
//        			timer.cancel(); // Terminate the timer thread
//                }
//            }
//		
//        }
//	}
	
	/*
	 * stop timer
	 */
	public void stopTimer()
	{
		if(timer != null)
		{
			timer.cancel();
			timertask.cancel();
			timertask = null;
			timer = null;
		}
	}

	/*
	 * task
	 */
	public void timerAction()
	{
		--distillation;
	
		if(distillation == 5)
		{
			tasks = 1;
//			still.setStatus_two(false);
////			departmentrepository.save(production);
			System.out.println("timer_two: " + still.getStatus_two());
			
		}
		
		if(distillation == 0)
		{
			still.setTimer_stop(true);	
//			departmentrepository.save(production);
			
			stopTimer();
			tasks = 2;
			System.out.println("timer: " + still.getTimer_stop());
		}
		
		System.out.println("timer: " + distillation);	
	}
	
	/*
	 * start timer
	 */
	public void startTimer()
	{
		if(timer == null)
		{		
			if(tasks < 2){

				timer = new Timer();
				timertask = new TimerTask()
				{
					public void run()
					{
						timerAction();
					}
				};
				timer.schedule(timertask, 0, 1 * 1000);
				
				if(tasks == 1){
					still.setStatus_two(false);
					departmentrepository.save(production);
				}
			}
		}
	}
}
