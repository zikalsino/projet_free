import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

const ReportList = () => {
  const [reports, setReports] = useState([]);

  // Effet pour récupérer les rapports depuis l'API
  useEffect(() => {
    axios.get('http://localhost:8080/api/reports/list')
      .then(response => {
        console.log(response.data); // Affiche les données de la réponse
        setReports(response.data); // Mettre à jour l'état avec les rapports
      })
      .catch(error => {
        console.error('Erreur lors de la récupération des rapports:', error);
      });
  }, []); // L'effet ne s'exécute qu'une seule fois lors du premier rendu du composant

  // Fonction pour supprimer un rapport
  const deleteReport = (id) => {
    console.log(`Tentative de suppression du rapport avec ID : ${id}`);
    axios.delete(`http://localhost:8080/api/reports/${id}`)
      .then(() => {
        // Supprimer le rapport de l'état local après suppression
        setReports(prevReports => prevReports.filter(report => report.id !== id));
      })
      .catch(error => {
        console.error('Erreur lors de la suppression du rapport:', error);
      });
  };

  return (
    <div className="report-list-container">
      <h1>Liste des Rapports</h1>
      <Link to="/report-generator" className="btn btn-primary">Créer un rapport</Link>
      <ul className="report-list">
        {reports.length > 0 ? (
          reports.map(report => (
            <li key={report.id} className="report-item">
              <h3>{report.title}</h3>
              <p>{report.description}</p>
              <p>Créé le : {report.createdDate}</p>
              <button onClick={() => deleteReport(report.id)} className="btn btn-danger">Supprimer</button>
              <Link to={`/report-generator/${report.id}`} className="btn btn-info">Modifier</Link>
            </li>
          ))
        ) : (
          <p>Aucun rapport disponible.</p>
        )}
      </ul>

      {/* Styles CSS */}
      <style jsx="true">{`
        .report-list-container {
          padding: 20px;
          max-width: 800px;
          margin: auto;
        }

        h1 {
          text-align: center;
          font-size: 2em;
          margin-bottom: 20px;
        }

        .btn {
          padding: 10px 15px;
          border-radius: 5px;
          font-size: 1em;
          cursor: pointer;
          text-decoration: none;
          margin: 5px;
        }

        .btn-primary {
          background-color: #007bff;
          color: white;
        }

        .btn-info {
          background-color: #17a2b8;
          color: white;
        }

        .btn-danger {
          background-color: #dc3545;
          color: white;
        }

        .report-list {
          list-style-type: none;
          padding: 0;
        }

        .report-item {
          border: 1px solid #ddd;
          padding: 15px;
          margin-bottom: 10px;
          border-radius: 5px;
        }

        .report-item h3 {
          margin: 0;
          font-size: 1.5em;
        }

        .report-item p {
          margin: 5px 0;
        }

        .report-item button {
          margin-right: 10px;
        }
      `}</style>
    </div>
  );
};

export default ReportList;
