package com.my.lukasz.apptravel.packlistgenerator.decisionTree;

import android.content.Context;

import com.my.lukasz.apptravel.packlistgenerator.ListItemsFromDb;
import com.my.lukasz.apptravel.packlistgenerator.PodrozUzytkownik;
import com.my.lukasz.apptravel.packlistgenerator.RzeczDoSpakowania;
import com.my.lukasz.apptravel.packlistgenerator.TravelsUsersFromDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DecisionTree {

    private Context context;
    private PodrozUzytkownik newUserData;

    public DecisionTree(Context context, PodrozUzytkownik newUserData) {
        this.context = context;
        this.newUserData = newUserData;
    }

    public List<RzeczDoSpakowania> getPackListRecommendation() throws IOException {
        List<PodrozUzytkownik> allDataAboutTravels =
                new ArrayList<>(TravelsUsersFromDB.getInstance(context).getPackLists().values());

        filterByTreeDecisions(allDataAboutTravels);

        PodrozUzytkownik closest = getTravelWithClosestDays(allDataAboutTravels);
        int travelId = closest.getTravelId();

        return ListItemsFromDb.getInstance(context).getPackLists().get(travelId);
    }

    private void filterByTreeDecisions(List<PodrozUzytkownik> allDataAboutTravels) {
        filterGender(allDataAboutTravels);
        filterWeatherType(allDataAboutTravels);
        filterTravelType(allDataAboutTravels);
        if (allDataAboutTravels.size() >= 5) {
            filterNumberOfDays(allDataAboutTravels);
        }
        if (allDataAboutTravels.size() >= 5) {
            filterTransportType(allDataAboutTravels);
        }
        if (allDataAboutTravels.size() >= 5) {
            filterAge(allDataAboutTravels);
        }
    }

    private PodrozUzytkownik getTravelWithClosestDays(List<PodrozUzytkownik> allDataAboutTravels) {
        int bestIndex = 0;
        int minDistance = Math.abs(allDataAboutTravels.get(0).getNumberOfDays() - newUserData.getNumberOfDays());
        for (int i = 0; i < allDataAboutTravels.size(); i++) {
            int currDistance = Math.abs(allDataAboutTravels.get(i).getNumberOfDays() - newUserData.getNumberOfDays());
            if (currDistance < minDistance) {
                bestIndex = i;
                minDistance = currDistance;
            }
        }
        return allDataAboutTravels.get(bestIndex);
    }

    private void filterAge(List<PodrozUzytkownik> allDataAboutTravels) {
        if (newUserData.getAge() < 11) {
            deleteAgeInGivenRange(11, 10000, allDataAboutTravels);
        }
        else if (newUserData.getAge() >= 11 && newUserData.getAge() <= 17) {
            deleteAgeInGivenRange(0, 10, allDataAboutTravels);
            deleteAgeInGivenRange(18, 10000, allDataAboutTravels);
        }
        else if (newUserData.getAge() >= 18 && newUserData.getAge() <= 35) {
            deleteAgeInGivenRange(0, 17, allDataAboutTravels);
            deleteAgeInGivenRange(36, 10000, allDataAboutTravels);
        }
        else if (newUserData.getAge() >= 36 && newUserData.getAge() <= 55) {
            deleteAgeInGivenRange(0, 35, allDataAboutTravels);
            deleteAgeInGivenRange(56, 10000, allDataAboutTravels);
        }
        else if (newUserData.getAge() >= 56 && newUserData.getAge() <= 69) {
            deleteAgeInGivenRange(0, 55, allDataAboutTravels);
            deleteAgeInGivenRange(70, 10000, allDataAboutTravels);
        }
        else if (newUserData.getAge() > 69) {
            deleteAgeInGivenRange(0, 69, allDataAboutTravels);
        }
    }

    private void filterTransportType(List<PodrozUzytkownik> allDataAboutTravels) {
        Iterator iterator = allDataAboutTravels.iterator();
        while (iterator.hasNext()) {
            PodrozUzytkownik podroz = (PodrozUzytkownik) iterator.next();
            if (podroz.getTransportTypeId() != newUserData.getTransportTypeId()) {
                iterator.remove();
            }
        }
    }

    private void filterNumberOfDays(List<PodrozUzytkownik> allDataAboutTravels) {
        if (newUserData.getNumberOfDays() == 1) {
            deleteDaysInGivenRange(2, 10000, allDataAboutTravels);
        }
        else if (newUserData.getNumberOfDays() > 1 && newUserData.getNumberOfDays() <= 3) {
            deleteDaysInGivenRange(0, 1, allDataAboutTravels);
            deleteDaysInGivenRange(4, 10000, allDataAboutTravels);
        }
        else if (newUserData.getNumberOfDays() > 3 && newUserData.getNumberOfDays() <= 6) {
            deleteDaysInGivenRange(0, 3, allDataAboutTravels);
            deleteDaysInGivenRange(7, 10000, allDataAboutTravels);
        }
        else if (newUserData.getNumberOfDays() > 6 && newUserData.getNumberOfDays() <= 10) {
            deleteDaysInGivenRange(0, 6, allDataAboutTravels);
            deleteDaysInGivenRange(11, 10000, allDataAboutTravels);
        }
        else if (newUserData.getNumberOfDays() > 10) {
            deleteDaysInGivenRange(0, 10, allDataAboutTravels);
        }
    }

    private void deleteDaysInGivenRange(int from, int to, List<PodrozUzytkownik> allDataAboutTravels) {
        Iterator iterator = allDataAboutTravels.iterator();
        while (iterator.hasNext()) {
            PodrozUzytkownik podroz = (PodrozUzytkownik) iterator.next();
            if (podroz.getNumberOfDays() >= from && podroz.getNumberOfDays() <= to) {
                iterator.remove();
            }
        }
    }

    private void deleteAgeInGivenRange(int from, int to, List<PodrozUzytkownik> allDataAboutTravels) {
        Iterator iterator = allDataAboutTravels.iterator();
        while (iterator.hasNext()) {
            PodrozUzytkownik podroz = (PodrozUzytkownik) iterator.next();
            if (podroz.getAge() >= from && podroz.getAge() <= to) {
                iterator.remove();
            }
        }
    }

    private void filterWeatherType(List<PodrozUzytkownik> allDataAboutTravels) {
        Iterator iterator = allDataAboutTravels.iterator();
        while (iterator.hasNext()) {
            PodrozUzytkownik podroz = (PodrozUzytkownik) iterator.next();
            if (podroz.getWeatherTypeId() != newUserData.getWeatherTypeId()) {
                iterator.remove();
            }
        }
    }

    private void filterTravelType(List<PodrozUzytkownik> allDataAboutTravels) {
        Iterator iterator = allDataAboutTravels.iterator();
        while (iterator.hasNext()) {
            PodrozUzytkownik podroz = (PodrozUzytkownik) iterator.next();
            if (podroz.getTravelTypeId() != newUserData.getTravelTypeId()) {
                iterator.remove();
            }
        }
    }

    private void filterGender(List<PodrozUzytkownik> allDataAboutTravels) {
        Iterator iterator = allDataAboutTravels.iterator();
        while (iterator.hasNext()) {
            PodrozUzytkownik podroz = (PodrozUzytkownik) iterator.next();
            if (!podroz.getGender().equals(newUserData.getGender())) {
                iterator.remove();
            }
        }
    }

    public PodrozUzytkownik getNewUserData() {
        return newUserData;
    }

    public void setNewUserData(PodrozUzytkownik newUserData) {
        this.newUserData = newUserData;
    }
}
