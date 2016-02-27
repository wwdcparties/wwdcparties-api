(ns wwdcparties.api.core
  (:require [wwdcparties.api.db :as db]
            [wwdcparties.api.foursquare :as foursquare]
            [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]))

(s/defschema Party {:approved Boolean
                    (s/optional-key :city) String
                    :description String
                    :end_time Long
                    (s/optional-key :event_url) String
                    :excerpt String
                    :location String
                    :meta {(s/optional-key :18+) Boolean
                           (s/optional-key :21+) Boolean
                           (s/optional-key :booze) Boolean
                           (s/optional-key :food) Boolean
                           (s/optional-key :ticket) Boolean}
                    :name String
                    :slug String
                    (s/optional-key :sponsor_name) String
                    (s/optional-key :sponsor_url) String
                    :start_time Long
                    :street_address String
                    (s/optional-key :twitter_handle) String
                    :type String
                    :types [(s/enum :party :meetup :presentation :outdoors)]})

(defapi party-api
  (ring.swagger.ui/swagger-ui "/api/docs")
  (swagger-docs
   {:info {:title "WWDC Parties API"}})
  (context* "/api/parties" []
            :tags ["Parties"]
            (GET* "/" []
                  :return [Party]
                  :summary "The full list of parties."
                  (ok (db/parties)))
            (GET* "/:slug" []
                  :return (s/maybe Party)
                  :path-params [slug :- String]
                  :summary "A single party by identifier."
                  (ok (db/parties slug)))))
