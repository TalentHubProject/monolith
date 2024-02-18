FROM gradle:jdk21

WORKDIR /app

COPY --chown=gradle:gradle . /app

CMD ["gradle", "--no-daemon", "bootRun"]