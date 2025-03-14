"use client";

import Link from "next/link";
import React, { useEffect, useState } from "react";
import { useArticlesStore } from "../../../utils/article/provider";
import { init } from "next/dist/compiled/webpack/webpack";

type ArticleType = {
  id: string;
  subject: string;
  content: string;
  createdAt: string;
  modifiedAt: string;
};

const page = () => {
  const { articles, ids, upload } = useArticlesStore((state) => state);

  const getArticles = async () => {
    const resp = await fetch("http://localhost:8090/api/v1/articles").then(
      (data) => data.json()
    );

    return resp.data;
  };

  const deleteArticle = async (id) => {
    const resp = await fetch(`http://localhost:8090/api/v1/articles/${id}`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    });

    if (resp.ok) {
      const articles = await getArticles();
      upload(articles);
    }
  };

  const initData = async () => {
    const initArticles = await getArticles(); // articles[], ids[];
    upload(initArticles);
  };

  useEffect(() => {
    initData();
  }, []);

  useEffect(() => {}, [articles]);

  return (
    <>
      <AddForm articles={articles} ids={ids} upload={upload} />
      <ul>
        {articles.map((e: ArticleType) => (
          <li key={`${e.id}_${e.subject}`}>
            {e.id} / <Link href={`/article/${e.id}`}>{e.subject}</Link> /{" "}
            {e.createdAt} /{" "}
            <button
              type="button"
              onClick={() => deleteArticle(e.id)}
              data-id={e.id}
            >
              삭제
            </button>
          </li>
        ))}
      </ul>
    </>
  );
};

const AddForm = ({ articles, ids, upload }: any) => {
  const [errMsg, setErrMsg] = useState("");

  const addArticle = async (rawData: { subject: string; content: string }) => {
    const resp = await fetch("http://localhost:8090/api/v1/articles", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(rawData),
    });

    return resp;
  };

  const handleSubmit = async (e: any) => {
    e.preventDefault();

    const rawData = {
      subject: e.target.subject.value,
      content: e.target.content.value,
    };

    const resp = await addArticle(rawData);

    if (resp.ok) {
      const newArticle = await resp.json();

      const newState = {
        articles: [...articles, newArticle.data.article],
        ids: [...ids, newArticle.data.article.id],
      };

      upload(newState);

      // e.target.subject.value = "";
      // e.target.content.value = "";
    } else {
    }
  };

  return (
    <>
      <form onSubmit={handleSubmit}>
        <h3>입력 폼</h3>
        <input type="text" name="subject" />
        <input type="text" name="content" />
        <button type="submit">등록</button>
      </form>
    </>
  );
};

export default page;
