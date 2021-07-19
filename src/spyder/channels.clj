(ns spyder.channels
  (:require [clojure.core.async :refer [>! <!! go chan]]
            [org.httpkit.client :as http]))


(defn make-str [url request]
  (str "#" url ": " (:content-length (:headers request))))

(defn async-get [url channel]
  (http/get url #(go (>! channel (make-str url %)))))

(defn async-wiki [urls]
  (let [reqs (count urls)
        channel (chan reqs)]
    ;; create requests
    (doseq [url urls]
      (async-get url channel))
    (doseq [_ (range 0 reqs)]
      (println (<!! channel)))))


