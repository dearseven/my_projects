package chat.sh.orz.cyan.mvvm.view;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.ObservableField;

import chat.sh.orz.cyan.mvvm.BR;


public class ViewObjJava extends BaseObservable {
    private ObservableField<String> nameJ = new ObservableField<String>("");

    private String _name;

    public void set_name(String _name) {
        this._name = _name;
        notifyPropertyChanged(BR._name);
    }

    @Bindable
    public String get_name() {
        return _name;
    }
}

