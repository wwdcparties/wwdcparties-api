(ns wwdcparties.party)

(def party-keys [:description :slug :meta :name :type
                 :start_time :excerpt :sponsor_url
                 :types :end_time :shortcode :event_url
                 :street_address :sponsor_name :location])

(defn tidy [party] (select-keys party party-keys))

(defn from-json [json] (tidy (assoc json :type "party")))

(defn approved
  ([party] (assoc party :approved true))
  ([party approved] (assoc party :approved approved)))

