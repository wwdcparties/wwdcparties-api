(ns wwdcparties.render.mapbox
  (:require [cemerick.url :refer [url-encode]]
            [clj-http.client :as http]
            [clojure.data.json :as json]
            [environ.core :refer [env]]
            [wwdcparties.render.colors :refer [color]]))

(def id (env :wwdc-parties-mapbox-id))
(def token (env :wwdc-parties-mapbox-token))

(defn encoded-address [party]
  (url-encode (str (:street_address party)
                   ", " 
                   (or (:city party) "San Francisco, CA"))))

(defn apple-maps-url [party]
  (str "http://maps.apple.com/?q=" (encoded-address party)))

(defn geocode-url [party]
  (str "https://api.tiles.mapbox.com/v4/geocode/mapbox.places/" (encoded-address party) ".json?access_token=" token))

(defn geocode [party]
  (-> party
      geocode-url
      http/get :body
      json/read-json
      :features first
      :geometry
      :coordinates))

(defn url [party]
  (let [coordinates (geocode party)
        longitude (first coordinates)
        latitude (second coordinates)]
    (str "https://api.tiles.mapbox.com/v4/" id "/"
         "pin-l+" (color party)
         "(" longitude "," latitude ")/"
         longitude "," latitude ",15/"
         "600x200@2x.png?access_token=" token)))
