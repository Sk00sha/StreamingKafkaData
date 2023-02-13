package org.sk00sha.Util;

import org.sk00sha.Pojos.GoalScorer;
import org.sk00sha.Pojos.MatchResult;
import org.sk00sha.Reader.CsvReader;
import org.sk00sha.Util.Helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that converts our data from batch to stream
 */
public abstract class BatchConvertor{
    /**
     *
     * @param fileSource - source file where we get Batch Data
     * @return
     */
    public List<GoalScorer> transformBatchGoalsToStream(String fileSource){
        List<GoalScorer> goalScorers=new ArrayList<>();
        var csvData= CsvReader.readSourceCsvData(fileSource);

        for (String[] row:csvData){
            goalScorers.add(new GoalScorer(
                    row[0],row[1],row[2],
                    row[3],row[4],row[5],
                    Helpers.convertStringToBoolean(row[6]),
                    Helpers.convertStringToBoolean(row[7])));
        }

        return goalScorers;
    }

    /**
     *
     * @param fileSource - source file where we get Batch Data
     * @return
     */
    public List<MatchResult> transformBatchDataMatchesToIndividualMatchResults(String fileSource){
        List<MatchResult> matchResultList=new ArrayList<>();
        var csvData= CsvReader.readSourceCsvData(fileSource);

        for (String[] row:csvData){
            matchResultList.add(new MatchResult(row[0],row[1],row[2],
                    Integer.parseInt(row[3]),
                    Integer.parseInt(row[4]),
                    row[5],row[6],row[7],
                    Helpers.convertStringToBoolean(row[8])));
        }

        return matchResultList;
    }
}
