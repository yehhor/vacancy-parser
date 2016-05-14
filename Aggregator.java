package parser.big01;



import parser.big01.model.HHStrategy;
import parser.big01.model.Model;
import parser.big01.model.MoikrugStrategy;
import parser.big01.model.Provider;
import parser.big01.view.HtmlView;

import java.io.IOException;

/**
 * Created by yehor on 22.02.2016.
 */
public class Aggregator
{
    public static void main(String[] args) throws IOException
    {
        HtmlView view = new HtmlView();
        Provider provider = new Provider(new HHStrategy());
        Provider providerKrug = new Provider(new MoikrugStrategy());
        Model model = new Model(view, providerKrug, provider);
        Controller controller = new Controller(model);
        view.setController(controller);
        view.userCitySelectEmulationMethod();
    }
}
