"use client";

import { useParams } from "next/navigation";
import React, { useEffect, useState } from "react";

interface Props {
  params: Promise<{ id: string }>;
}

type ArticleType = {
  resultCode: string;
  msg: string;
  data: {
    article: {
      id: string;
      subject: string;
      content: string;
      createdAt: string;
      modifiedAt: string;
    };
  };
};

const page = () => {
  //   const { id } = await params;
  const { id } = useParams();

  const [article, setArticle] = useState({
    subject: "",
    content: "",
  });

  useEffect(() => {
    loadArticle(id);
  }, []);

  const loadArticle = (id: string) => {
    fetch(`http://localhost:8090/api/v1/articles/${id}`)
      .then((result) => result.json())
      .then((result) => {
        setArticle(result.data.article);
      });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const resp = await fetch(`http://localhost:8090/api/v1/articles/${id}`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(article),
    });

    if (resp.ok) {
      alert("ok");
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setArticle({ ...article, [name]: value });
  };

  return (
    <>
      <h1>수정페이지</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="subject"
          value={article.subject}
          onChange={handleChange}
        />
        <input
          type="text"
          name="content"
          value={article.content}
          onChange={handleChange}
        />
        <button type="submit">수정</button>
      </form>
    </>
  );
};

export default page;
