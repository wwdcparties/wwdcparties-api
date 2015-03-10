(ns wwdcparties.party)

(defrecord Party [description slug meta name
                  start_time excerpt sponsor_url
                  types end_time shortcode event_url
                  street_address sponsor_name location])

(defn from-json [json]
  (map->Party json))

(defn approved
  ([party] (assoc party :approved true))
  ([party approved] (assoc party :approved approved)))

