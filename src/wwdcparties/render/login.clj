(ns wwdcparties.render.login
  (:require [hiccup.form :refer :all]
            [ring.middleware.anti-forgery :refer :all]
            [ring.util.anti-forgery :refer :all]))

(defn form []
  (list
   [:head]
   [:body
    (form-to [:post "/admin/login"]
             (text-field "username")
             (password-field "password")
             (submit-button "Submit")
             (anti-forgery-field))]))
