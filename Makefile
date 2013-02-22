DATE=$(shell date +%I:%M%p)
CHECK=\033[32m✔\033[39m
HR=\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#\#

# use a version number for cache busting
VERSION = $(shell cat VERSION)
# CSS FILE TARGETS
CSS_FILE = "war/content/build/cafe.${VERSION}.css"
CSS_GZIP_FILE = "war/content/build/cafe.min.${VERSION}.gz.css"
# JS FILE TARGETS
JS_FILE = "war/content/build/cafe.min.${VERSION}.js"
JS_UGLY_FILE = "war/content/build/cafe.min.${VERSION}.ugly.js"
JS_GZIP_FILE = "war/content/build/cafe.min.${VERSION}.gz.js"

build:
	@echo "\n${HR}"
	@echo "Building Assets... version: ${VERSION}"
	@echo "${HR}\n"
	mkdir -p war/content/build
# concat all the cs files (the first overwrites, the rest append)
	cat war/content/dev/css/bootstrap.css > ${CSS_FILE}
	cat war/content/dev/css/bootstrap-responsive.css >> ${CSS_FILE}
	@echo "\n${HR}"
	@echo "Joining CSS...	 ${CHECK} Done"
	@echo "${HR}\n"
	gzip -9 -c ${CSS_FILE} > ${CSS_GZIP_FILE}
	@echo "\n${HR}"
	@echo "Gzipping CSS...	 ${CHECK} Done"
	@echo "${HR}\n"
	@echo "Assets successfully built at ${DATE}."
	@echo "${HR}\n"
# concat all the js files (the first overwrites, the rest append)
	cat war/content/dev/js/libs/bootstrap.js > ${JS_FILE}
	@echo "\n${HR}"
	@echo "Joining JS...	 ${CHECK} Done"
	@echo "${HR}\n"
	./node_modules/.bin/uglifyjs -nc ${JS_FILE} > ${JS_UGLY_FILE}
	@echo "\n${HR}"
	@echo "Uglifying JS...	 ${CHECK} Done"
	@echo "${HR}\n"
	gzip -9 -c ${JS_UGLY_FILE} > ${JS_GZIP_FILE}
	@echo "\n${HR}"
	@echo "Gzipping JS...	 ${CHECK} Done"
	@echo "${HR}\n"
	@echo "Assets successfully built at ${DATE}."
	@echo "${HR}\n"
	@echo "Uploading to S3"
	@echo "${HR}\n"
	ruby scripts/upload_to_s3.rb suprcache.com ${JS_GZIP_FILE} 'text/javascript'
#	ruby scripts/upload_to_s3.rb suprcache.com ${CSS_GZIP_FILE} 'text/css'
	@echo "\n${HR}"
	@echo "Uploading to S3...	 ${CHECK} Done"
	@echo "${HR}\n"
	
.PHONY: bootstrap
bootstrap:
	@$(MAKE) bootstrap -C $@;
# copy the files we need over
	cp bootstrap/bootstrap/css/bootstrap.css war/content/dev/css/
	cp bootstrap/bootstrap/css/bootstrap-responsive.css war/content/dev/css/
	cp bootstrap/bootstrap/js/bootstrap.js war/content/dev/js/libs/
	cd ..; 
	
clean:
	rm -r war/content/build
	
