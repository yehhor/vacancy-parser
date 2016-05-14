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
 * Created by yehor on 22.02.2016.
 */
public class HHStrategy implements Strategy
{
    private static final String URL_FORMAT = "http://hh.ua/search/vacancy?text=java+%s&page=%d";
    private static final String CLIENT = "Chrome/48.0.2564.116";
    private static final String REFERRER = "google.ru";

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

    @Override
    public List<Vacancy> getVacancies(String searchString)
    {
        List<Vacancy> vacancies = new ArrayList<>();
        try {
            int pageNumber = 0;
            Document doc;
            while (true) {
                doc = getDocument(searchString, pageNumber++);
                if (doc == null) break;
                Elements elements = doc.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
                if (elements.size() == 0) break;
                for (Element element : elements) {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setUrl(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").attr("href"));

                    vacancy.setTitle(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").text());
                    vacancy.setCity(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address").text());
                    vacancy.setCompanyName(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text());
                    vacancy.setSiteName("http://hh.ua");
                    Element salaryElement = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation").first();
                    String salary = "";
                    if (salaryElement != null) {
                        salary = salaryElement.text();
                    }
                    vacancy.setSalary(salary);
                    vacancies.add(vacancy);
                }
            }
        }
        catch (Exception e) {
        }
        return vacancies;
    }


}
