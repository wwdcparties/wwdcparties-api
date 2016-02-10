(ns wwdcparties.api.foursquare
  (:require [cemerick.url :refer [url-encode]]
            [clj-http.client :as http]
            [clojure.data.json :refer [read-json]]
            [environ.core :refer [env]]))

(def foursquare-client-id (env :wwdc-parties-foursquare-id))
(def foursquare-secret (env :wwdc-parties-foursquare-secret))

(defn suggestions-url [query]
  (str "https://api.foursquare.com/v2/venues/suggestcompletion"
       "?v=20150428"
       "&near=San%20Francisco,%20CA"
       "&client_id=" foursquare-client-id
       "&client_secret=" foursquare-secret
       "&query=" (url-encode query)))

(defn suggestions-json [query]
  (-> query
      suggestions-url
      http/get
      :body
      read-json))

(defn parse-suggestion [json]
  {:name (:name json)
   :address (:address (:location json))})

(defn suggestions [query]
  (map parse-suggestion 
       (-> query
           suggestions-json
           :response
           :minivenues)))
