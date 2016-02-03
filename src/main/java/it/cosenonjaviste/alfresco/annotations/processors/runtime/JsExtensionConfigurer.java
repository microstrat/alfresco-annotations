/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.cosenonjaviste.alfresco.annotations.processors.runtime;

import it.cosenonjaviste.alfresco.annotations.JsExtension;
import org.alfresco.repo.processor.BaseProcessorExtension;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;


/**
 * <tt>BeanPostProcessor</tt> for {@link it.cosenonjaviste.alfresco.annotations.JsExtension} annotation.
 *
 * <p>
 *     This processor register {@link JsExtension#value()} as <strong>extension name</strong>
 * </p>
 *
 * @author Andrea Como
 */
@Component
public class JsExtensionConfigurer implements BeanPostProcessor {

    private static Log LOGGER = LogFactory.getLog(BehaviourConfigurer.class);

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof BaseProcessorExtension && bean.getClass().getAnnotation(JsExtension.class) != null) {
            BaseProcessorExtension extension = (BaseProcessorExtension) bean;
            extension.setExtensionName(extension.getClass().getAnnotation(JsExtension.class).value());
            LOGGER.debug("Extension name set to bean " + beanName);
        }
        return bean;
    }
}