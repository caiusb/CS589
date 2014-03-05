#!/bin/sh

mkdir analysis

python understandTime.py
Rscript buildCSVs.r 
Rscript rq.r