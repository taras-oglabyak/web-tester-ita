package com.softserve.webtester.editor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Component;

import com.softserve.webtester.model.Label;
import com.softserve.webtester.service.MetaDataService;

/**
 * Implementation of CustomCollectionEditor for binding spring form attribute with labels List in {@link Request} class.
 * 
 * @author Taras Oglabyak
 *
 */
@Component
public class LabelEditor extends CustomCollectionEditor {

    @Autowired
    private MetaDataService metaDataService;

    public LabelEditor() {
        super(List.class);
    }

    @Override
    protected Label convertElement(Object element) {
        if (element != null) {
            int id = Integer.parseInt(element.toString());
            Label label = metaDataService.loadLabelById(id);
            return label;
        }
        return null;
    }
}