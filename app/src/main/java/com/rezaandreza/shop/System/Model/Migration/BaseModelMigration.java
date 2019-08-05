package com.rezaandreza.shop.System.Model.Migration;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.rezaandreza.shop.Helper.Debug;

import static com.rezaandreza.shop.System.Helper.TypeCasting.toObject;

public class BaseModelMigration {
    public Map<String,String> map =  new HashMap<String,String>();
    public Map<String,String> fixedData =  new HashMap<String,String>();

    public ArrayList<Object> migrate(Class targetClass,ArrayList<?> data)
    {
        ArrayList<Object> baseModels = new ArrayList<>();
        for(Object ob : data)
        {
            try {
                Object baseModel = targetClass.newInstance();
                for(Field f : targetClass.getDeclaredFields())
                {
                    try {
                        if(this.map.get(f.getName())!=null) {
                            if(this.map.get(f.getName())!="") {
                                try {
                                    Field obf = ob.getClass().getDeclaredField(map.get(f.getName()));
                                    if(this.fixedData.get(this.map.get(f.getName()))!=null)
                                    {
                                        f.set(baseModel, toObject(f.getGenericType().toString(), this.fixedData.get(this.map.get(f.getName()))));
                                    }
                                    else
                                    {
                                        f.set(baseModel, toObject(f.getGenericType().toString(), obf.get(ob).toString()));
                                    }
                                }catch (Exception e){
                                    if(this.fixedData.get(this.map.get(f.getName()))!=null)
                                    {
                                        f.set(baseModel, toObject(f.getGenericType().toString(), this.fixedData.get(this.map.get(f.getName()))));
                                    }
                                    System.out.println(e.getMessage());
                                }
                            }
                        }
                        else
                        {
                            try {
                                Field obf = ob.getClass().getDeclaredField(f.getName());
                                if (this.fixedData.get(f.getName()) != null) {
                                    f.set(baseModel, toObject(f.getGenericType().toString(), this.fixedData.get(f.getName())));
                                } else {
                                    f.set(baseModel, toObject(f.getGenericType().toString(), obf.get(ob).toString()));
                                }
                            }catch (Exception e){
                                if (this.fixedData.get(f.getName()) != null) {
                                    f.set(baseModel, toObject(f.getGenericType().toString(), this.fixedData.get(f.getName())));
                                }
                                System.out.println(e.getMessage());
                            }
                        }
                    }catch (Exception e){System.out.println(e.getMessage());}
                }
                baseModels.add(baseModel);
            }catch (Exception e){System.out.println(e.getMessage());}
        }
        return baseModels;
    }
}
