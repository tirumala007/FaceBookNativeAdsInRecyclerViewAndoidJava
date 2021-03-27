# FaceBookNativeAdsInRecyclerViewAndoidJava
FB native ads in recycler view android java


in Main Activity, 

 //making the model list to pass to the adapter,  you can follow the same below step for firebase projects also
 
        List<MainModel> modelList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            modelList.add(new MainModel("This contains Native Ads ", "From Facebook Audience Network " ,"After every 5th item",
                    "Current item "+ i));

            ///check if the current item is 5th, add a null object, so that we can inflate native ad in adapter
            if(adCounter%5==0)
            {


                //we will check this, later in get item vew type to add native ads to recycler view
                modelList.add(null);
            }
            //increase the counter after every model item
            adCounter++;

        }

        // create the adapter instance
        MyCustomAdapter adapter = new MyCustomAdapter(MainActivity.this, modelList );

        //set the adapter to reycler view
        rv.setAdapter(adapter);
        
        
 //in your custom adapter, check for null object
     
     
    @Override
    public int getItemViewType(int position) {

        //her checking, if the list contains a null object,
        if (objectList.get(position) ==null) {

            //it conatins null, so we are returning the AD view
            return ADS_TYPE;
        } else {
            //here it doesn't contain null, so we are returning the regular item view type
            return ITEM_TYPE;
        }
    }
    
    
    
 // in your custom adpater, Oncreate method, check the view type to inflate various views
 
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //chec if it is a Ads types
        if (viewType == ADS_TYPE) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            //this view will be displayed when there is not ad available.
            View adLayoutOutline = inflater
                    .inflate(R.layout.item_facebook_native_ad_outline, parent, false);
            ViewGroup vg = adLayoutOutline.findViewById(R.id.ad_container);

            NativeAdLayout adLayoutContent = (NativeAdLayout) inflater
                    .inflate(R.layout.facebook_native_ad_item, parent, false);
            vg.addView(adLayoutContent);
            return new AdViewHolder(adLayoutOutline);
            
        } else {

            //here it is the item, so return regular item layout
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_item, parent, false));
        }
    }
    
    
   //add these(below steps) in manifest file application tag

    //step 1-> set android:largeHeap="true"
    //step 2-> set android:hardwareAccelerated="true"
    //step 3-> create application class, extends application, initialize facebook ads there
    //android:name=".MyAppliation"


    //this step is for version 24 or above, mine  phone's is  23,so i don't need it
    //step 4->  create a xml file in RES
    // android:networkSecurityConfig="@xml/network_security_config"
    
     <?xml version="1.0" encoding="utf-8"?>
     <network-security-config>
      <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">127.0.0.1</domain>
      </domain-config>
     </network-security-config>
