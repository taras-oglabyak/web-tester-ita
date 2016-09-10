package com.softserve.webtester.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Data transfer object class for transferring results of one collection or requests list run. Includes such fields as
 * collectionId, requestResultDTOList. In case when we run one request or requests list, collectionId = 0.
 *
 * @author Anton Mykytiuk
 */
public class CollectionResultDTO implements Serializable {

    private static final long serialVersionUID = 5567389541983839270L;

    private int collectionId;
    private List<RequestResultDTO> requestResultDTOList;

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public List<RequestResultDTO> getRequestResultDTOList() {
        return requestResultDTOList;
    }

    public void setRequestResultDTOList(List<RequestResultDTO> requestResultDTOList) {
        this.requestResultDTOList = requestResultDTOList;
    }
}
