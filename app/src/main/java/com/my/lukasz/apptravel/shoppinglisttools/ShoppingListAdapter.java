package com.my.lukasz.apptravel.shoppinglisttools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.my.lukasz.apptravel.R;
import com.my.lukasz.apptravel.activities.EditShoppingListItemActivity;
import com.my.lukasz.apptravel.db.AppDatabase;
import com.my.lukasz.apptravel.db.entities.ElementListyDoSpakowania;
import com.mynameismidori.currencypicker.CurrencyPicker;
import com.mynameismidori.currencypicker.CurrencyPickerListener;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListAdapter extends ArrayAdapter<ElementListyDoSpakowania> {

    private Context context;
    private int mResource;
    private ArrayList<ElementListyDoSpakowania> lista;
    private CheckBox checkboxItem;
    private TextView menuItem;
    private TextView circleCounter;
    private TextView price;
    private String waluta;
    private ElementListyDoSpakowania elementListyDoSpakowania;
    private AppDatabase mDb=AppDatabase.getInstance(context);


    public ShoppingListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ElementListyDoSpakowania> objects) {
        super(context, resource, objects);
        this.context=context;
        this.lista=objects;
        mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        boolean czyDoZakupu = getItem(position).isCzyPrzekazanoDoZakupu();
        boolean czyKupione = getItem(position).isCzyKupione();
        double cena = getItem(position).getCena();
        String nazwa = getItem(position).getNazwa();
        int ilosc=getItem(position).getIloscDoZakupu();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(mResource,parent,false);

        checkboxItem=convertView.findViewById(R.id.checkBoxBought);
        menuItem=convertView.findViewById(R.id.shoppingListItemtextViewOptions);
        circleCounter=convertView.findViewById(R.id.shoppinglistitemcirclecounter);
        price=convertView.findViewById(R.id.textViewPrice);

        checkboxItem.setTag(position);
        checkboxItem.setOnCheckedChangeListener(checkListener);
        menuItem.setTag(position);

        elementListyDoSpakowania =getItem(position);

        checkboxItem.setText(elementListyDoSpakowania.getNazwa());
        circleCounter.setText(String.valueOf(elementListyDoSpakowania.getIloscDoZakupu()));
        price.setText(R.string.pricelabel);
        price.append(" ");
        if(Double.toString(elementListyDoSpakowania.getCena()).equals("0.0")){
            price.append("?");
        }
        else {
            price.append(String.format("%.2f",elementListyDoSpakowania.getCena()));
            price.append(" ");
            price.append(elementListyDoSpakowania.getWaluta());
        }



        if(mDb.elementListyDoSpakowaniaDao().getElementListyDoSpakowaniaById(elementListyDoSpakowania.getId()).isCzyKupione()){
            checkboxItem.setChecked(true);
            checkboxItem.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

        menuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();
                ElementListyDoSpakowania elementListyDoSpakowania=getItem(position);

                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.inflate(R.menu.packlistitemmenu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.editpacklistitem:
                                Intent intent = new Intent(context, EditShoppingListItemActivity.class);
                                intent.putExtra("itemId", elementListyDoSpakowania.getId());
                                context.startActivity(intent);
                                break;
                            case R.id.deletepacklistitem:
                                mDb.elementListyDoSpakowaniaDao().
                                        setCzyDoZakupu(elementListyDoSpakowania.getId(),false);
                                updateReceiptsList(mDb.elementListyDoSpakowaniaDao().getAllElementyDoZakupu(elementListyDoSpakowania.getListaDoSpakowaniaId(),true));
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        return convertView;

    }

    CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener()
    {

            @Override
            public void onCheckedChanged (CompoundButton buttonView,boolean isChecked)
            {

                    int position = (int) buttonView.getTag();
                    elementListyDoSpakowania = getItem(position);
                    double cena=mDb.elementListyDoSpakowaniaDao().getElementListyDoSpakowaniaById(elementListyDoSpakowania.getId()).getCena();
                    if (cena == 0 && isChecked) {
                        buttonView.setChecked(false);
                        mDb.elementListyDoSpakowaniaDao().setCzyKupione(elementListyDoSpakowania.getId(), false);


                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle(R.string.providepricelabel);

                        alert.setMessage(R.string.providepricemessage);
                        final EditText input = new EditText(context);
                        final EditText currnecy = new EditText(context);
                        final TextView walutatv=new TextView(context);
                        walutatv.setText(context.getResources().getString(R.string.currencylabel));
                        walutatv.setTextColor(context.getResources().getColor(R.color.black));
                        currnecy.setCursorVisible(false);
                        currnecy.setText(mDb.podrozDao().getWalutaByTravelId(mDb.listaDoSpakowaniaDao().getPodrozIdFromListaDoSpakowaniaId(elementListyDoSpakowania.getListaDoSpakowaniaId())));
                        waluta=currnecy.getText().toString();
                        LinearLayout container = new LinearLayout(context);
                        container.setOrientation(LinearLayout.VERTICAL);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(dpToPx(20), 0, dpToPx(20), 0);



                        input.setLayoutParams(lp);
                        currnecy.setLayoutParams(lp);
                        walutatv.setLayoutParams(lp);

                        currnecy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CurrencyPicker picker = CurrencyPicker.newInstance("Select Currency");

                                picker.show(((FragmentActivity)context).getSupportFragmentManager(), "CURRENCY_PICKER");

                                picker.setListener(new CurrencyPickerListener() {
                                    @Override
                                    public void onSelectCurrency(String name, String code, String dialCode, int flagDrawableResID) {
                                        currnecy.setText(code);
                                        InputMethodManager inputMethodManager =(InputMethodManager)context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                                        inputMethodManager.hideSoftInputFromWindow(picker.getView().getWindowToken(), 0);
                                        waluta=code;
                                        picker.dismiss();

                                    }
                                });
                            }

                        });

                        currnecy.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if (hasFocus) {
                                    CurrencyPicker picker = CurrencyPicker.newInstance("Select Currency");
                                    picker.show(((FragmentActivity)context).getSupportFragmentManager(), "CURRENCY_PICKER");

                                    picker.setListener(new CurrencyPickerListener() {
                                        @Override
                                        public void onSelectCurrency(String name, String code, String dialCode, int flagDrawableResID) {
                                            currnecy.setText(code);
                                            InputMethodManager inputMethodManager =(InputMethodManager)context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                                            inputMethodManager.hideSoftInputFromWindow(picker.getView().getWindowToken(), 0);
                                            waluta=code;
                                            picker.dismiss();
                                        }
                                    });
                                }
                            }
                        });

                        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        input.setHint(R.string.pricelabel);
                        container.addView(input);
                        container.addView(walutatv);
                        container.addView(currnecy);




                        alert.setPositiveButton(R.string.submitpricelabel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Editable s = input.getText();

                                if (s.toString().length() > 9)Toast.makeText(context, R.string.maxninenumbers, Toast.LENGTH_LONG).show();
                                else if("".equals(s.toString().trim()) || s.toString()==null){
                                    Toast.makeText(context, R.string.badpriceformat, Toast.LENGTH_LONG).show();
                                    }
                                else if (".".equals(s.toString())) {
                                    // priceInputLayout.setError(getString(R.string.badpriceformat));
                                    Toast.makeText(context, R.string.badpriceformat, Toast.LENGTH_LONG).show();
                                } else if (s.toString().contains(".")) {
                                    int integerPlaces = s.toString().indexOf('.');
                                    int decimalPlaces = s.toString().length() - integerPlaces - 1;
                                    if (decimalPlaces > 2) {
                                        // priceInputLayout.setError(getString(R.string.decimalplaceserror));
                                        Toast.makeText(context, R.string.decimalplaceserror, Toast.LENGTH_LONG).show();
                                    } else if (Double.parseDouble(s.toString()) <= 0) {
                                        //   priceInputLayout.setError(getString(R.string.pricemorethanzeroerror));
                                        Toast.makeText(context, R.string.pricemorethanzeroerror, Toast.LENGTH_LONG).show();
                                    } else {

                                        mDb.elementListyDoSpakowaniaDao().updateCenaElementZakupuById(getItem(position).getId(), Double.parseDouble(s.toString()),waluta);

                                 //       elementListyDoSpakowania.setCena(Double.parseDouble(s.toString()));
                                        mDb.elementListyDoSpakowaniaDao().setCzyKupione(getItem(position).getId(), true);
                                        updateReceiptsList(mDb.elementListyDoSpakowaniaDao().getAllElementyDoZakupu(elementListyDoSpakowania.getListaDoSpakowaniaId(), true));
                                        buttonView.setChecked(true);
                                        dialog.cancel();
                                        //  checkIfEnableButton();
                                    }
                                } else if (!s.toString().isEmpty() && Double.parseDouble(s.toString()) <= 0) {
                                    //  priceInputLayout.setError(getString(R.string.pricemorethanzeroerror));
                                    Toast.makeText(context, R.string.pricemorethanzeroerror, Toast.LENGTH_LONG).show();
                                } else {
                                    mDb.elementListyDoSpakowaniaDao().updateCenaElementZakupuById(getItem(position).getId(), Double.parseDouble(s.toString()),waluta);

                              //      elementListyDoSpakowania.setCena(Double.parseDouble(s.toString()));
                                    mDb.elementListyDoSpakowaniaDao().setCzyKupione(getItem(position).getId(), true);
                                    updateReceiptsList(mDb.elementListyDoSpakowaniaDao().getAllElementyDoZakupu(elementListyDoSpakowania.getListaDoSpakowaniaId(), true));
                                    buttonView.setChecked(true);
                                    dialog.cancel();
                                }

                            }
                        });

                        alert.setNegativeButton(R.string.cancellabel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog dialog = alert.create();
                        dialog.setView(container);
                        dialog.show();
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Editable s = input.getText();

                                if (s.toString().length() > 9) {
                                    //     priceInputLayout.setError(getString(R.string.maxninenumbers));
                                    Toast.makeText(context, R.string.maxninenumbers, Toast.LENGTH_LONG).show();
                                }
                                else if("".equals(s.toString().trim()) || s.toString()==null){
                                    Toast.makeText(context, R.string.badpriceformat, Toast.LENGTH_LONG).show();
                                }
                                else if (".".equals(s.toString())) {
                                    // priceInputLayout.setError(getString(R.string.badpriceformat));
                                    Toast.makeText(context, R.string.badpriceformat, Toast.LENGTH_LONG).show();
                                } else if (s.toString().contains(".")) {
                                    int integerPlaces = s.toString().indexOf('.');
                                    int decimalPlaces = s.toString().length() - integerPlaces - 1;
                                    if (decimalPlaces > 2) {
                                        // priceInputLayout.setError(getString(R.string.decimalplaceserror));
                                        Toast.makeText(context, R.string.decimalplaceserror, Toast.LENGTH_LONG).show();
                                    } else if (Double.parseDouble(s.toString()) <= 0) {
                                        //   priceInputLayout.setError(getString(R.string.pricemorethanzeroerror));
                                        Toast.makeText(context, R.string.pricemorethanzeroerror, Toast.LENGTH_LONG).show();
                                    } else {
                                        mDb.elementListyDoSpakowaniaDao().updateCenaElementZakupuById(getItem(position).getId(), Double.parseDouble(s.toString()),waluta);

                                        mDb.elementListyDoSpakowaniaDao().setCzyKupione(getItem(position).getId(), true);

                                        updateReceiptsList(mDb.elementListyDoSpakowaniaDao().getAllElementyDoZakupu(elementListyDoSpakowania.getListaDoSpakowaniaId(), true));
                                        buttonView.setChecked(true);
                                        dialog.cancel();
                                        //  checkIfEnableButton();
                                    }
                                } else if (!s.toString().isEmpty() && Double.parseDouble(s.toString()) <= 0) {
                                    //  priceInputLayout.setError(getString(R.string.pricemorethanzeroerror));
                                    Toast.makeText(context, R.string.pricemorethanzeroerror, Toast.LENGTH_LONG).show();
                                } else {
                                    mDb.elementListyDoSpakowaniaDao().updateCenaElementZakupuById(getItem(position).getId(), Double.parseDouble(s.toString()),waluta);

                                    mDb.elementListyDoSpakowaniaDao().setCzyKupione(getItem(position).getId(), true);

                                    updateReceiptsList(mDb.elementListyDoSpakowaniaDao().getAllElementyDoZakupu(elementListyDoSpakowania.getListaDoSpakowaniaId(), true));
                                    buttonView.setChecked(true);
                                    dialog.cancel();
                                }
                            }
                        });


                        //   Toast.makeText(context,R.string.nopricetobouy, Toast.LENGTH_LONG).show();
                    } else if (cena > 0 && isChecked) {
                        mDb.elementListyDoSpakowaniaDao().setCzyKupione(elementListyDoSpakowania.getId(), isChecked);
                        buttonView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    } else if (cena == 0 && !isChecked) {
                        buttonView.setPaintFlags(buttonView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                        mDb.elementListyDoSpakowaniaDao().setCzyKupione(elementListyDoSpakowania.getId(), isChecked);
                    } else if (cena > 0 && !isChecked) {
                        buttonView.setPaintFlags(buttonView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                        mDb.elementListyDoSpakowaniaDao().setCzyKupione(elementListyDoSpakowania.getId(), isChecked);
                    } else {
                        buttonView.setPaintFlags(buttonView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                        mDb.elementListyDoSpakowaniaDao().setCzyKupione(elementListyDoSpakowania.getId(), isChecked);
                    }

                }


    };

    public void updateReceiptsList(List<ElementListyDoSpakowania> newlist) {
        lista.clear();
        lista.addAll(newlist);
        notifyDataSetChanged();
    }
    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
