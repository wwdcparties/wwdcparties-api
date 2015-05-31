(ns wwdcparties.party)

(def party-keys [:description :slug :meta :name :type
                 :start_time :excerpt :sponsor_url
                 :types :end_time :shortcode :event_url
                 :street_address :sponsor_name :location
                 :twitter_handle :approved])

(defn tidy [party] (select-keys party party-keys))

(defn from-json [json] (tidy json))

(defn approved
  ([party] (assoc party :approved true))
  ([party approved] (assoc party :approved approved)))

