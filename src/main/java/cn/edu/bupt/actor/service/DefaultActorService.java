package cn.edu.bupt.actor.service;

import akka.actor.*;
import cn.edu.bupt.actor.actors.Session.SessionActorProcessor;
import cn.edu.bupt.actor.actors.Session.SessionManagerActor;
import cn.edu.bupt.message.FromSessionActorToDeviceActorMsg;
import cn.edu.bupt.message.SessionAwareMsg;
import cn.edu.bupt.message.SessionCtrlMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by Administrator on 2018/4/17.
 */
@Service
public class DefaultActorService implements SessionMsgProcessor{

    private static final String ACTOR_SYSTEM_NAME = "Akka";
    public static final String CORE_DISPATCHER_NAME = "core-dispatcher";

    @Autowired
    private ActorSystemContext actorContext;

    private ActorSystem system;

    private ActorRef sessionManagerActor;

    @PostConstruct
    public void initActorSystem(){
        //actorContext.setSessionManagerActor(this);
        system = ActorSystem.create(ACTOR_SYSTEM_NAME, actorContext.getConfig());
        actorContext.setActorSystem(system);

        sessionManagerActor = system.actorOf(Props.create(new SessionManagerActor.ActorCreator(actorContext)).withDispatcher(CORE_DISPATCHER_NAME),
                "sessionManagerActor");
        actorContext.setSessionManagerActor(sessionManagerActor);
        actorContext.setSessionManagerActor(sessionManagerActor);
    }


    @PreDestroy
    public void stopActorSystem() {
        Future<Terminated> status = system.terminate();
        try {
            Terminated terminated = Await.result(status, Duration.Inf());
        } catch (Exception e) {

        }
    }

    @Override
    public void process(SessionAwareMsg sessionMsg) {
        sessionManagerActor.tell(sessionMsg,ActorRef.noSender());
    }
}
