#!/bin/bash

lein clean
lein cljsbuild once min
cd resources/public
aws s3 sync . s3://tempcalc.com/ --acl public-read
