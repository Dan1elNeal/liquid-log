package ru.naumen.sd40.log.parser;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by doki on 22.10.16.
 */
public class ActionDoneParser
{
    private static Set<String> EXCLUDED_ACTIONS = new HashSet<>();

    static
    {
        EXCLUDED_ACTIONS.add("EventAction".toLowerCase());
    }

    Pattern doneRegEx = Pattern.compile("Done\\((\\d+)\\): ?(.*?Action)");

    public void parseLine(DataSet dataSet, String line)
    {
        Matcher matcher = doneRegEx.matcher(line);
        ActionDoneData data = dataSet.getActionsDone();

        if (matcher.find())
        {
            String actionInLowerCase = matcher.group(2).toLowerCase();
            if (EXCLUDED_ACTIONS.contains(actionInLowerCase))
            {
                return;
            }

            data.addTime(Integer.parseInt(matcher.group(1)));
            if (actionInLowerCase.equals("addobjectaction"))
            {
                data.addAddObjectAction();
            }
            else if (actionInLowerCase.equals("editobjectaction"))
            {
                data.addEditObjectAction();
            }
            else if (actionInLowerCase.matches("getcatalogsaction"))
            {
                data.addGetCatalogsAction();
            }
            else if (actionInLowerCase.matches("(?i)[a-zA-Z]+comment[a-zA-Z]+"))
            {
                data.addCommentAction();
            }
            else if (!actionInLowerCase.contains("advlist")
                    && actionInLowerCase.matches("(?i)^([a-zA-Z]+|Get)[a-zA-Z]+List[a-zA-Z]+"))

            {
                data.addGetListAction();
            }
            else if (actionInLowerCase.matches("(?i)^([a-zA-Z]+|Get)[a-zA-Z]+Form[a-zA-Z]+"))
            {
                data.addGetFormAction();
            }
            else if (actionInLowerCase.matches("(?i)^([a-zA-Z]+|Get)[a-zA-Z]+DtObject[a-zA-Z]+"))
            {
                data.addGetGtObjectAction();
            }
            else if (actionInLowerCase.matches("(?i)[a-zA-Z]+search[a-zA-Z]+"))
            {
                data.addSearchAction();
            }

        }
    }
}
