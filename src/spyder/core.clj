(ns spyder.core
  (:require [spyder.channels :as channels]
            [spyder.futures :as futures])
  (:gen-class))



(defn pages-list [start end]
  (map #(format "https://en.wikipedia.org/wiki/%d" %) (range start end)))

(defn -main []
  (let [urls (pages-list 0 50)]
    (time (channels/async-wiki urls))
    (time
     (let [futures (doall (map futures/callback-wiki urls))]
     (doseq [resp futures]
       @resp)))))
