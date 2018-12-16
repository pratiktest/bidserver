package com.bidproject.service.bidserver.scheduler;

import com.bidproject.service.bidserver.bid.Bid;
import com.bidproject.service.bidserver.bid.BidRepository;
import com.bidproject.service.bidserver.project.Project;
import com.bidproject.service.bidserver.project.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTasks {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BidRepository bidRepository;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    //schedule every 2 minutes
    @Scheduled(fixedRate = 1000*60*2)
    public void notifyBidders() {
        try{
            List<Project> projects = projectRepository.findAll();
            for(Project project: projects){
                if(project.getExpiry().compareTo(new Date()) < 0 ){

                    if(!project.getWinner()) {
                        int updated = projectRepository.updateWinner(project.getId());
                        if (updated == 0) {
                            //winner was updated by someone else and cleanup was already performed
                            continue;
                        }
                        //notify the bidder
                        log.info("bidder "+ project.getMinBidder() + " won the bid");
                    }

                    //archive the project or delete it after 10 days of its expiry
                    Date today = new Date();
                    if(project.getExpiry().compareTo(new Date(today.getTime() - (10* 1000 * 60 * 60 * 24))) <= 0){
                        projectRepository.delete(project);
                    }

                    //clean up the bid history table
                    List<Bid> bids = bidRepository.findByProjectId(project.getId());
                    for(Bid b : bids){
                        bidRepository.delete(b);
                    }
                }
            }
        } catch(Throwable e){
            log.info("error in notifying bidder or cleaning up entries "+ e.getMessage());
        }

    }
}
