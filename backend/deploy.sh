aws cloudformation package --template-file sam.yaml --output-template-file output-sam.yaml --s3-bucket rabdo
aws cloudformation deploy --template-file output-sam.yaml --stack-name RabdomanteApi --capabilities CAPABILITY_IAM
