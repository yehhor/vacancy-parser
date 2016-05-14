package parser.big01.model;

import parser.big01.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yehor on 24.02.2016.
 */
public class MoikrugStrategy implements Strategy
{
    private static final String URL_FORMAT = "https://moikrug.ru/vacancies?q=java+%s&page=%d";
    private static final String CLIENT = "Chrome/48.0.2564.116";
    private static final String REFERRER = "google.ru";

    @Override
    public List<Vacancy> getVacancies(String searchString)
    {
        List<Vacancy> vacancies = new ArrayList<>();
        try {
            int pageNumber = 1;
            Document doc;
            while (true) {
                try
                {
                    doc = getDocument(searchString, pageNumber++);
                    if (doc == null) break;
                    Elements elements = doc.getElementsByAttributeValue("class", "job  ");
                    elements.addAll(doc.getElementsByAttributeValue("class", "job marked"));
                    if (elements.size() == 0) break;
                    for (Element element : elements)
                    {
                        Vacancy vacancy = new Vacancy();
                        vacancy.setUrl("https://moikrug.ru" + element.getElementsByAttributeValue("class", "title").first().
                                getElementsByTag("a").attr("href"));
                        vacancy.setTitle(element.getElementsByAttributeValue("class", "title").first().text());
                        Element city = element.getElementsByAttributeValue("class", "location").first();
                        if (city == null)
                            vacancy.setCity("");
                        else
                            vacancy.setCity(city.text());
                        vacancy.setCompanyName(element.getElementsByAttributeValue("class", "company_name").first().getElementsByTag("a").text());
                        vacancy.setSiteName("https://MoiKrug.ru");
                        Element salaryElement = element.getElementsByAttributeValue("class", "salary").first();
                        String salary = "";
                        if (salaryElement != null)
                        {
                            salary = salaryElement.text();
                        }
                        vacancy.setSalary(salary);
                        vacancies.add(vacancy);
                    }
                }catch (NullPointerException e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e) {
        }
        return vacancies;
    }

    protected Document getDocument(String searchString, int page) throws IOException
    {
        String url = String.format(URL_FORMAT, searchString, page);
        Document doc = null;
        try{
            doc = Jsoup.connect(url).userAgent(CLIENT).referrer(REFERRER).get();
        }catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return doc;
    }
}
