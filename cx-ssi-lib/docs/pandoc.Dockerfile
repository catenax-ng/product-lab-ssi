FROM pandoc/latex

COPY . /data
WORKDIR /data
RUN pandoc -s -o Documentation.pdf /data/README.md /data/Architecture.md /data/FeatureSpec-*.md /data/Test.md

ENTRYPOINT ["tail", "-f", "/dev/null"]