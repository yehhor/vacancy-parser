package parser.big01;

import parser.big01.model.Model;


/**
 * Created by yehor on 22.02.2016.
 */
public class Controller
{
    private Model model;

    public Controller(Model model)
    {
        if (model == null)
            throw new IllegalArgumentException();
        this.model = model;
    }

    public void onCitySelect(String cityName)
    {
        model.selectCity(cityName);
    }
}
