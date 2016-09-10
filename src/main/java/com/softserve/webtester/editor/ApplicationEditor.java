package com.softserve.webtester.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.softserve.webtester.model.Application;
import com.softserve.webtester.service.MetaDataService;

/**
 * Implementation of PropertyEditorSupport for binding spring form attribute with application field in {@link Request}
 * class.
 * 
 * @author Taras Oglabyak
 *
 */
@Component
public class ApplicationEditor extends PropertyEditorSupport {

    @Autowired
    private MetaDataService metaDataService;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Application instance = metaDataService.applicationLoad(Integer.parseInt(text));
        this.setValue(instance);
    }
}