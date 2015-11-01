package com.apps.poi.presenters.list;

import com.apps.poi.models.data.PointPOJO;

import java.util.ArrayList;

/**
 * This class contains the methods to search into a list.
 *
 * Created by Victor Tellez on 27/10/2015.
 */
public class SearchHelper {

    /**
     * Goes through the given list and filters it according to the given query.
     *
     * @param pointPOJOArrayList    list given as search sample
     * @param query             to be searched
     * @return                  new filtered list
     */
    public static ArrayList<PointPOJO> performSearch(ArrayList<PointPOJO> pointPOJOArrayList, String query) {

        // First we split the query so that we are able to search word by word (in lower case)
        String[] queryByWords = query.toLowerCase().split("\\s+");

        // Empty list to fill with matches
        ArrayList<PointPOJO> pointsFiltered = new ArrayList<PointPOJO>();

        // Go through initial releases and perform search
        for (PointPOJO pointPOJO : pointPOJOArrayList) {

            // Content to search through (in lower case)
            String content = (
                    pointPOJO.getTitle()).toLowerCase();

            for (String word : queryByWords) {

                // There is a match only if all of the words are contained
                int numberOfMatches = queryByWords.length;

                // All query words have to be contained, otherwise the release is filtered out
                if (content.contains(word)) {
                    numberOfMatches--;
                } else {
                    break;
                }

                // They all match
                if (numberOfMatches == 0) {
                    pointsFiltered.add(pointPOJO);
                }
            }
        }

        return pointsFiltered;
    }

}
