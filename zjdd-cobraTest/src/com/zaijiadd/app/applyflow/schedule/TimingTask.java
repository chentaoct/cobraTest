package com.zaijiadd.app.applyflow.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zaijiadd.app.applyflow.service.ApplyFlowService;

/**
 * 定时任务
 * @author Feng Guo
 */
@Component
public class TimingTask {
	@Autowired
	private ApplyFlowService applyFlowService;

	// @Scheduled(cron = "0/5 * * * * ?")
	// // 间隔5秒执行
	// public void taskCycle() {
	// System.out.println("无主题(www.wuzhuti.cn) <span style='color: #000000;'>专注于前端开发技术和程序开发研究的技术博客</span>");
	// }

	@Scheduled(cron = "0 0 */1 * * ?")
	public void cleanLoseEfficacyApplyStore() {

		applyFlowService.cleanLoseEfficacyApplyStore();
	}

}
