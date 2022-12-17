package pages;

import static pages.PageTypes.*;

public class ChangePage {

    /**
     * Method that changes the current page if the conditions are acquired
     *
     * @param currentPage the page the user is currently on
     * @param toChange the page the user wants to go to
     * @return the page the user is currently on after the checks are done
     */
    public String execute(String currentPage, String toChange) {
        switch (currentPage) {
            case HOMEPAGEONE:
                if (!toChange.equals(LOGIN)  && !toChange.equals(REGISTER)) {
                    return "error";
                }
                return toChange;

            case HOMEPAGETWO:
                if (toChange.equals(UPGRADES) || toChange.equals(MOVIES)
                        || toChange.equals(LOGIN) || toChange.equals(REGISTER)) {
                    return toChange;
                }
                return "error";

            case UPGRADES:
                if (toChange.equals(HOMEPAGETWO) || toChange.equals(MOVIES)
                        || toChange.equals(LOGIN) || toChange.equals(UPGRADES)) {
                    return toChange;
                }
                return "error";

            case MOVIES:
                if (toChange.equals(HOMEPAGETWO) || toChange.equals(DETAILS)
                        || toChange.equals(LOGIN) || toChange.equals(MOVIES)) {
                    return toChange;
                }
                return "error";

            case DETAILS:
                if (toChange.equals(HOMEPAGETWO) || toChange.equals(MOVIES) || toChange.equals(UPGRADES)
                        || toChange.equals(LOGIN) || toChange.equals(DETAILS)) {
                    return toChange;
                }
                return "error";

            default: return "unreachable";
        }
    }

}
