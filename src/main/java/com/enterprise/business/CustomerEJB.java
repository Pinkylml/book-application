package com.enterprise.business;

import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.enterprise.domain.Item;

@Stateless
public class CustomerEJB {

    @Resource
    private TimerService timerService;

    @PersistenceContext(unitName = "bookApplicationPU")
    private EntityManager em;

    public void scheduleDynamicPriceAudit(Item item, String scheduledHour, String scheduledMinute) {
        em.persist(item);

        ScheduleExpression schedule = new ScheduleExpression()
                .hour(scheduledHour)
                .minute(scheduledMinute)
                .second("0");

        // 3. Package the entity into a persistent timer configuration object
        // true = persistent (ensures this timer survives application crashes or
        // restarts)
        TimerConfig timerConfig = new TimerConfig(item, true);

        // 4. Register the execution request into the scheduling pool
        timerService.createCalendarTimer(schedule, timerConfig);
        System.out.println("--> [CustomerEJB] Programmatically registered an audit timer for item ID: " + item.getId());
    }

    @Timeout // The single explicit container callback method designated for timer events
    public void executePriceAudit(Timer timer) {
        // Retrieve the serialized domain entity wrapped inside our timer record
        Item item = (Item) timer.getInfo();
        System.out.println("--> [Execution Trigger] Running automated price evaluation for item: " + item.getTitle()
                + " (" + item.getPrice() + " " + item.getCurrency() + ")");
    }
}
