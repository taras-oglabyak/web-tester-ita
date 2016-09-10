package com.softserve.webtester.editor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Component;

import com.softserve.webtester.model.Request;
import com.softserve.webtester.model.RequestCollection;
import com.softserve.webtester.service.RequestService;

/**
 * Implementation of CustomCollectionEditor for binding spring form attribute
 * with requests List in {@link RequestCollection} class.
 * 
 * @author Yura Lubinec
 *
 */
@Component
public class RequestEditor extends CustomCollectionEditor {

    @Autowired
    private RequestService requestService;

    public RequestEditor() {
        super(List.class);
    }

    @Override
    protected Request convertElement(Object element) {
        if (element != null) {
            int id = Integer.parseInt(element.toString());
            Request request = requestService.load(id);
            return request;
        }
        return null;
    }

}
