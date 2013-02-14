package se.joakimkemeny.demo.factory.websocket;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

public class WebSocketControllerBeanPostProcessor implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {

        final Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(WebSocketController.class);
        WebSocketManager webSocketManager = applicationContext.getBean(WebSocketManager.class);

        for (final Object controller : controllers.values()) {
            final Class<? extends Object> controllerClass = controller.getClass();
            final WebSocketController annotation = controllerClass.getAnnotation(WebSocketController.class);

            webSocketManager.registerDelegateForProtocol(controller, annotation.value());
        }
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
