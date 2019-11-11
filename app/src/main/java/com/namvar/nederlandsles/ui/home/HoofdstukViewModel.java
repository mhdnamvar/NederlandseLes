package com.namvar.nederlandsles.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HoofdstukViewModel extends ViewModel {

    private MutableLiveData<List<String>> mList;

    public HoofdstukViewModel() {
        List<String> list = new ArrayList<>();
        list.add("1. Hij weet dat hij lief is.");
        list.add("2. Zij zeggen dat hij binnen is.");
        list.add("3. Ik vind dat wij hard werken.");
        list.add("4. Jij wil dat zij morgen komen.");
        list.add("5. Wij hopen dat het in juli warm weer wordt.");
        list.add("6. Zij denkt dat hij deze week komt.");
        list.add("7. Ik denk dat hij Nederlands spreekt.");
        list.add("8. Zij weet dat het regent.");
        list.add("9. Ik denk dat ze komen.");
        list.add("10. Hij ziet dat zijn jas vies is.");
        list.add("11. De kinderen willen dat hun moeder thuis komt.");
        list.add("12. Mijn moeder zegt dat de soup klaar is.");
        list.add("13. Vier dieven zeggen dat zij onschuldig zijn.");
        list.add("14. Ik wil dat jij naar mijn huid komt.");
        list.add("15. Wij zeggen dat wij hard werken.");
        list.add("16. Vader wil dat hij deze week komt.");
        list.add("17. Zij denkt dat ik op vakantie ga.");
        list.add("18. De studenten vinden dat het examen makkelijk is");
        list.add("19. De docent wil dat ik goed studeer.");
        list.add("20. De minister zegt dat hij naar onze stad komt.");
        list.add("21. Wij hopen dat wij in de voetbal wedstrijd winnen.");
        list.add("22. De buurvrouw vindt dat ik een mooi huid heb.");
        list.add("23. Mevrouw Jansen denk dat ik lieg.");
        list.add("24. Zij gelooft dat ze windt.");

        mList = new MutableLiveData<>();
        mList.setValue(list);
    }

    public LiveData<List<String>> getList() {
        return mList;
    }
}
