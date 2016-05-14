package parser.big01.view;

import parser.big01.Controller;
import parser.big01.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.List;

/**
 * Created by yehor on 23.02.2016.
 */
public class HtmlView implements View
{
    private Controller controller;

    private final String filePath = "./src/"
            + this.getClass().getPackage().getName().replaceAll("\\.", "/")
            + "/vacancies.html";



    @Override
    public void update(List<Vacancy> vacancies)
    {
        try{
            updateFile(getUpdatedFileContent(vacancies));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod()
    {
        controller.onCitySelect("Dnepropetrovsk");
    }

    private String getUpdatedFileContent(List<Vacancy> vacancies)
    {
        if (vacancies == null)
            throw new IllegalArgumentException();
        Document doc = null;
        try{
            doc = getDocument();
            while(!doc.getElementsByAttributeValue("class", "vacancy").isEmpty())
                doc.getElementsByAttributeValue("class", "vacancy").remove();
            Element template = doc.getElementsByClass("template").first().clone();
            template.removeClass("template");
            template.removeAttr("style");
            for (Vacancy vacancy : vacancies)
            {
                Element element = template.clone();
                element.getElementsByClass("title").first().empty()
                        .html(String.format("<a href=\"%s\">%s</a>", vacancy.getUrl(), vacancy.getTitle()));
                element.getElementsByClass("city").first().empty().html(vacancy.getCity());
                element.getElementsByClass("companyName").first().empty().html(vacancy.getCompanyName());
                element.getElementsByClass("salary").first().empty().html(vacancy.getSalary());
                doc.getElementsByAttributeValue("class", "vacancy template").before(element.outerHtml());
            }
            return doc.outerHtml();

        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private void updateFile(String dir)
    {
        if (dir == null)
            throw new IllegalArgumentException();
        File file = new File(filePath);
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(dir);
            writer.close();
        }catch (IOException e)
        {
        }
    }

    protected Document getDocument() throws IOException
    {
        Document doc = Jsoup.parse(new File(filePath), "UTF-8");
        return doc;
    }
}
