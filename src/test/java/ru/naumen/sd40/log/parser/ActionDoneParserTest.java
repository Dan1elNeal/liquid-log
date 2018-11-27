package ru.naumen.sd40.log.parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.naumen.sd40.log.parser.Sdng.ActionDoneData;
import ru.naumen.sd40.log.parser.Sdng.ActionDoneParser;
import ru.naumen.sd40.log.parser.Sdng.SdngDataSet;

public class ActionDoneParserTest {
    private ActionDoneParser parser;
    private SdngDataSet dataSet;
    private ActionDoneData actionDoneData;

    @Before
    public void setup() {
        parser = new ActionDoneParser();
        dataSet = new SdngDataSet();
        actionDoneData = dataSet.getActionsDone();
    }

    @Test
    public void mustParseAddAction() {
        //when
        parser.parseLine(dataSet, "Done(10): AddObjectAction");

        //then
        Assert.assertEquals(1, actionDoneData.getAddObjectActions());
    }

    @Test
    public void mustParseFormActions() {
        //when
        parser.parseLine(dataSet, "Done(10): GetFormAction");
        parser.parseLine(dataSet,"Done(1): GetAddFormContextDataAction");

        //then
        Assert.assertEquals(2, actionDoneData.getFormActions());
    }

    @Test
    public void mustParseEditActions() {
        //when
        parser.parseLine(dataSet,"Done(10): EditObjectAction");

        //then
        Assert.assertEquals(1, actionDoneData.getEditObjectsActions());
    }

    @Test
    public void mustParseGetCatalogsAction() {
        //when
        parser.parseLine(dataSet,"Done(10):GetCatalogsAction");

        //then
        Assert.assertEquals(1, actionDoneData.getGetCatalogsActions());
    }

    @Test
    public void mustParseSearchActions(){
        //when
        parser.parseLine(dataSet,"Done(10): GetPossibleAgreementsChildsSearchAction");
        parser.parseLine(dataSet,"Done(10): TreeSearchAction");
        parser.parseLine(dataSet,"Done(10): GetSearchResultAction");
        parser.parseLine(dataSet,"Done(10): GetSimpleSearchResultsAction");
        parser.parseLine(dataSet,"Done(10): SimpleSearchAction");
        parser.parseLine(dataSet,"Done(10): ExtendedSearchByStringAction");
        parser.parseLine(dataSet,"Done(10): ExtendedSearchByFilterAction");

        //then
        Assert.assertEquals(7, actionDoneData.getSearchActions());
    }

    @Test
    public void mustParseGetListActions(){
        //when:
        parser.parseLine(dataSet,"Done(10): GetDtObjectListAction");
        parser.parseLine(dataSet,"Done(10): GetPossibleCaseListValueAction");
        parser.parseLine(dataSet,"Done(10): GetPossibleAgreementsTreeListActions");
        parser.parseLine(dataSet,"Done(10): GetCountForObjectListAction");
        parser.parseLine(dataSet,"Done(10): GetDataForObjectListAction");
        parser.parseLine(dataSet,"Done(10): GetPossibleAgreementsListActions");
        parser.parseLine(dataSet,"Done(10): GetDtObjectForRelObjListAction");

        //then:
        Assert.assertEquals(7, actionDoneData.geListActions());
    }

    @Test
    public void mustParseCommentActions(){
        //when:
        parser.parseLine(dataSet,"Done(10): EditCommentAction");
        parser.parseLine(dataSet,"Done(10): ChangeResponsibleWithAddCommentAction");
        parser.parseLine(dataSet,"Done(10): ShowMoreCommentAttrsAction");
        parser.parseLine(dataSet,"Done(10): CheckObjectsExceedsCommentsAmountAction");
        parser.parseLine(dataSet,"Done(10): GetAddCommentPermissionAction");
        parser.parseLine(dataSet,"Done(10): GetCommentDtObjectTemplateAction");

        //then:
        Assert.assertEquals(6, actionDoneData.getCommentActions());
    }

    @Test
    public void mustParseDtObjectActions(){
        //when:
        parser.parseLine(dataSet,"Done(10): GetVisibleDtObjectAction");
        parser.parseLine(dataSet,"Done(10): GetDtObjectsAction");
        parser.parseLine(dataSet,"Done(10): GetDtObjectTreeSelectionStateAction");
        parser.parseLine(dataSet,"Done(10): AbstractGetDtObjectTemplateAction");
        parser.parseLine(dataSet,"Done(10): GetDtObjectTemplateAction");

        //then:
        Assert.assertEquals(5, actionDoneData.getDtObjectActions());
    }

}
