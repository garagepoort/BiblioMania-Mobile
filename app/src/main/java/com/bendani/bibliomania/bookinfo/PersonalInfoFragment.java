package com.bendani.bibliomania.bookinfo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bendani.bibliomania.R;
import com.bendani.bibliomania.books.domain.PersonalBookInfo;
import com.bendani.bibliomania.generic.presentation.customview.FloatingLabelTextview;

public class PersonalInfoFragment extends Fragment {


    private PersonalBookInfo personalBookInfo;

    private FloatingLabelTextview giftInfoFromTextView;
    private FloatingLabelTextview giftInfoOccasionTextView;
    private FloatingLabelTextview giftInfoDateTextView;
    private LinearLayout giftInfoLayout;


    private FloatingLabelTextview buyInfoShopTextView;
    private FloatingLabelTextview buyInfoReasonTextView;
    private FloatingLabelTextview buyInfoCityCountryShopTextView;
    private FloatingLabelTextview buyInfoDateTextView;
    private LinearLayout buyInfoLayout;

    public PersonalInfoFragment() {}

    public void setPersonalBookInfo(PersonalBookInfo personalBookInfo) {
        this.personalBookInfo = personalBookInfo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);
        giftInfoLayout = (LinearLayout) view.findViewById(R.id.gift_info_layout);
        buyInfoLayout = (LinearLayout) view.findViewById(R.id.buy_info_layout);

        giftInfoFromTextView = (FloatingLabelTextview) view.findViewById(R.id.gift_info_from);
        giftInfoOccasionTextView = (FloatingLabelTextview) view.findViewById(R.id.gift_info_occasion);
        giftInfoDateTextView = (FloatingLabelTextview) view.findViewById(R.id.gift_info_date);

        buyInfoShopTextView = (FloatingLabelTextview) view.findViewById(R.id.buy_info_shop);
        buyInfoReasonTextView = (FloatingLabelTextview) view.findViewById(R.id.buy_info_reason);
        buyInfoCityCountryShopTextView = (FloatingLabelTextview) view.findViewById(R.id.buy_info_city_country_shop);
        buyInfoDateTextView = (FloatingLabelTextview) view.findViewById(R.id.buy_info_date);

        if(personalBookInfo.getGiftInfo() != null){
            loadGiftInfo();
            giftInfoLayout.setVisibility(View.VISIBLE);
            buyInfoLayout.setVisibility(View.GONE);
        }else{
            loadBuyInfo();
            buyInfoLayout.setVisibility(View.VISIBLE);
            giftInfoLayout.setVisibility(View.GONE);
        }

        return view;
    }

    private void loadGiftInfo(){
        giftInfoFromTextView.setText(personalBookInfo.getGiftInfo().getFrom());
        giftInfoOccasionTextView.setText(personalBookInfo.getGiftInfo().getOccasion());
        if(personalBookInfo.getGiftInfo().getGiftDate() != null){
            giftInfoDateTextView.setText(personalBookInfo.getGiftInfo().getGiftDate().toString());
        }
    }
    private void loadBuyInfo(){
        if(personalBookInfo.getBuyInfo().getBuyDate() != null){
            buyInfoDateTextView.setText(personalBookInfo.getBuyInfo().getBuyDate().toString());
        }
        buyInfoCityCountryShopTextView.setText(personalBookInfo.getBuyInfo().getCityShop() + " " + personalBookInfo.getBuyInfo().getCountryShop());
        buyInfoReasonTextView.setText(personalBookInfo.getBuyInfo().getReason());
        buyInfoShopTextView.setText(personalBookInfo.getBuyInfo().getShop());
    }

}
