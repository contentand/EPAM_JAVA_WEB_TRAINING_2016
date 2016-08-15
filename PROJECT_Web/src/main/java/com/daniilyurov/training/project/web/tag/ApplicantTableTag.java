package com.daniilyurov.training.project.web.tag;

import com.daniilyurov.training.project.web.model.business.impl.output.ApplicantInfoItem;
import com.daniilyurov.training.project.web.utility.SessionAttributes;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Set;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.BUNDLE;


/**
 * Draws a table with a specified header and applicant info as content.
 *
 * @author Daniil Yurov
 */
public class ApplicantTableTag extends TagSupport {

    static final Logger logger = Logger.getLogger(ApplicantTableTag.class);

    private String headName;
    private Set<ApplicantInfoItem> applicants;
    private boolean isButtonPresent;

    @SuppressWarnings("unused") // set by jsp
    public void setHeadName(String headName) {
        this.headName = headName;
    }

    @SuppressWarnings("unused") // set by jsp
    public void setApplicants(Set<ApplicantInfoItem> applicants) {
        this.applicants = applicants;
    }

    @SuppressWarnings("unused") // set by jsp
    public void setButtonPresent(boolean buttonPresent) {
        isButtonPresent = buttonPresent;
    }

    @Override
    public int doStartTag() throws JspException {

        try {
            ResourceBundle bundle = (ResourceBundle) pageContext.getSession().getAttribute(BUNDLE);
            String tableName = bundle.getString(headName);
            String name = bundle.getString("page.name");
            String surname = bundle.getString("page.surname");
            String score = bundle.getString("page.result");

            JspWriter output = pageContext.getOut();

            if (applicants.size() == 0) {
                return SKIP_BODY;
            }

            output.write("<table class='applicants-list'>");
            output.print("<thead>");
            output.write("<tr><th colspan='4'>" + tableName + "</th><tr>");
            output.print("<tr>");
            output.print("<th>" + name +"</th>");
            output.print("<th>" + surname +"</th>");
            output.print("<th>" + score +"</th>");
            if(isButtonPresent) output.print("<th></th>");
            output.print("</tr>");
            output.print("</thead>");
            output.write("<tbody>");
        } catch (IOException e) {
            logger.error("Failed to write tag.", e);
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {

        if (applicants.size() == 0) {
            return EVAL_PAGE;
        }

        JspWriter output = pageContext.getOut();
        try {
            for (ApplicantInfoItem info : applicants) {

                output.write("<tr>");
                output.write("<td>");
                output.write(info.getLocalFirstName());
                output.write("</td>");
                output.write("<td>");
                output.write(info.getLocalLastName());
                output.write("</td>");
                output.write("<td>");
                output.print(info.getTotalScore());
                output.write("</td>");
                if(isButtonPresent) printButton(output, info.getApplicationId());
                output.write("</tr>");
            }
            output.write("</tbody></table>");
        } catch (IOException e) {
            logger.error("Failed to write tag.", e);
            throw new JspTagException(e.getMessage());
        }
        return EVAL_PAGE;
    }

    private void printButton(JspWriter out, Long id) throws IOException {

        out.write("<td>");
        out.write("<form action='" + pageContext.getServletContext().getContextPath() + "/application/" + id +
                "/consider' method='POST'>");

        out.print("<input type='hidden' name='afterProcessDestinationPath' value='");
        out.print(pageContext.getSession().getAttribute(SessionAttributes.ATTRIBUTE_CURRENT_PAGE_LINK));
        out.print("'/>");

        out.print("<input type='hidden' name='csrfToken' value='");
        out.print(pageContext.getSession().getAttribute(SessionAttributes.CSRF_TOKEN_SERVER));
        out.print("'/>");

        ResourceBundle bundle = (ResourceBundle) pageContext.getSession().getAttribute(BUNDLE);
        String buttonLabel = bundle.getString("page.consider");

        out.print("<input class=\"small-btn\" type=\"submit\" value=\""+ buttonLabel + "\"/>");
        out.print("</form>");
        out.print("</td>");
        out.print("</td>");
    }
}
