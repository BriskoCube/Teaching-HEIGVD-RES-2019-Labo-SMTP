<div style="text-align: right;">Julien Quartier & Nathan Séville</div>

# Teaching-HEIGVD-RES-2019-Labo-SMTP

## Description
Simple SMTP client implemented in JAVA.
The app group emails randomly from a user defined list.
Each of the groups have a uniq sender, message and recipients addresses.


## Setup instructions

### Mockmock on Docker

> MockMock is a cross-platform SMTP server built on Java.
>
> MockMock GitHub: <https://github.com/tweakers/MockMock>

> If you don't have docker installed, install it before following following instructions.

In project's root directory build docker image with:

- `docker build -t mockmock .`

Create docker mockmock container:

- `docker run -d -p 2525:2525 -p 8282:8282 --name mockmock mockmock`

You can now access mockmock web interface at docker's container ip address on port `8282`.

You can stop container at any time with:

- `docker stop mockmock`

Start it again with:

- `docker start mockmock`

Delete container with:

- `docker rm mockmock`

### Prank campaign configuration
The app is configured with three files.
#### config.properties
This file is used for basic app configuration. 
* `serverhost` define the mail server ip address or hostname
* `serverport` define the mail server port
* `groupcount` the number of spamming groups to create randomly
```
serverhost=smtp.mailtrap.io
serverport=2525

groupcount=60
```

#### messages.json
In this file you can define each spam email. You can define a `subject` and a `body` value. You can add as much email as you want.
One email is selected randomly for each group. 
```json
[
  {
    "subject": "Morbi vulputate ultricies",
    "body": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce pharetra malesuada luctus. Morbi vulputate ultricies euismod. Ut consequat hendrerit viverra. Etiam faucibus posuere sapien ac feugiat. Morbi faucibus elit ac nibh blandit, a venenatis dolor porta. Phasellus scelerisque scelerisque elit, ac facilisis erat gravida sit amet. \r\nClass aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Mauris elementum scelerisque arcu, nec posuere risus efficitur sit amet. Fusce pharetra tempor ex, ac maximus massa facilisis eu. Vivamus et sem dui. Vestibulum sapien ligula, venenatis ut dictum sit amet, aliquam nec risus. Nam eget aliquam tellus, rhoncus lacinia sem. \r\nVestibulum congue elit orci, eu sodales velit egestas et."
  },
  {
    "subject": "Nunc maximus",
    "body": "Aliquam erat volutpat.\r\n\r\nPraesent dignissim nulla eu neque feugiat rhoncus. Nunc maximus mattis est quis ornare. Vivamus sit amet felis egestas, scelerisque massa eget, ultrices mauris. Proin tristique leo eu enim rhoncus, eu ornare arcu tempus. Curabitur sit amet euismod est. Integer lectus nisl, finibus ac gravida eget, posuere at ex. \r\nNunc ut euismod tellus. Integer nec elit auctor, egestas ante et, tincidunt risus. Mauris ut ex eget velit consequat posuere at nec leo. Donec eu sapien at erat iaculis dapibus."
  }
]
```

#### victims.list
This file contains a list of all target email. Each address is separated with a new line.
Example with random emails:
```
teverett@att.net
dkeeler@mac.com
techie@gmail.com
jdhedden@outlook.com
ullman@yahoo.com
oechslin@verizon.net
rupak@live.com
```


## Implementation description

![Uml](./figures/uml.png)



Your report MUST include the following sections:

* **A brief description of your project**: if people exploring GitHub find your repo, without a prior knowledge of the RES course, they should be able to understand what your repo is all about and whether they should look at it more closely.

* **Instructions for setting up a mock SMTP server (with Docker)**. The user who wants to experiment with your tool but does not really want to send pranks immediately should be able to use a mock SMTP server. For people who are not familiar with this concept, explain it to them in simple terms. Explain which mock server you have used and how you have set it up.

* **Clear and simple instructions for configuring your tool and running a prank campaign**. If you do a good job, an external user should be able to clone your repo, edit a couple of files and send a batch of e-mails in less than 10 minutes.

* **A description of your implementation**: document the key aspects of your code. It is probably a good idea to start with a class diagram. Decide which classes you want to show (focus on the important ones) and describe their responsibilities in text. It is also certainly a good idea to include examples of dialogues between your client and an SMTP server (maybe you also want to include some screenshots here).

