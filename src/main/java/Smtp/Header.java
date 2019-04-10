package Smtp;

public class Header {
    enum HeaderType {
        FROM("From"),
        TO("To"),
        SUBJECT("Subject"),
        DATE("Date");

        private String name;

        HeaderType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    HeaderType headerType;
    String value;

    public Header(HeaderType headerType, String value) {
        this.headerType = headerType;
        this.value = value;
    }

    @Override
    public String toString() {
        return headerType + ": " + value;
    }
}
