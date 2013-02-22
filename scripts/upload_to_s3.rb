require 'aws-sdk'

(bucket_name, file_name, content_type) = ARGV
unless bucket_name && file_name
  puts "Usage: upload_file.rb <BUCKET_NAME> <FILE_NAME> <CONTENT_TYPE>"
  exit 1
end

s3 = AWS::S3.new(
  :access_key_id => 'AKIAJERJ4NL7PSJTLXOA',
  :secret_access_key => '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')

# get the suprcache bucket
b = s3.buckets.create(bucket_name)

# upload the file
basename = File.basename(file_name)
o = b.objects['assets/' + basename]
o.write(:file => file_name,:acl => 'public_read',:content_type => content_type,:reduced_redundancy => true,:content_encoding => 'gzip',:cache_control => 'max-age=315360000')

# 'text/javascript'