(ns wwdcparties.routes.home
  (:require [clojure.java.io :as io]
            [compojure.core :refer [defroutes GET POST]]
            [hiccup.page :refer [html5]]
            [hiccup.core :refer [h]]
            [ring.util.http-response :refer [moved-permanently found not-found]]
            [ring.util.response :refer [content-type response]]
            [wwdcparties.api.db :as db]
            [wwdcparties.api.submit :as sub]
            [wwdcparties.render.calendar :as cal]
            [wwdcparties.render.index :as index]
            [wwdcparties.render.info :as info]
            [wwdcparties.render.submit :as submit]))

(defn index-page []
  (html5 (index/page (sort-by :start_time (db/parties)))))

(defn info-page [slug]
  (let [party (db/parties slug)]
    (if (empty? party)
      (not-found)
      (html5 (info/page party)))))

(defn submit-page []
  (html5 (submit/page nil)))

(defn submitted-page [request]
  (db/submit-party (sub/form->party request))
  (html5 "yay"))

(defn calendar []
  (cal/calendar (db/parties)))

(defroutes home-routes
  (GET "/" [] (moved-permanently "/parties/"))
  (GET "/parties/" [] (index-page))
  (GET "/parties/new" [] (submit-page))
  (POST "/parties/new" request (submitted-page (:params request)))
  (GET "/parties/:slug" [slug] (info-page slug))
  (GET "/calendar" [] (-> (calendar) response (content-type "text/calendar"))))
